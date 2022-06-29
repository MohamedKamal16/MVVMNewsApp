package com.androiddevs.mvvmnewsapp.ui.adapter

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.databinding.ItemCountryBinding
import com.androiddevs.mvvmnewsapp.model.dataClass.CountryCategory
import com.androiddevs.mvvmnewsapp.ui.activity.NewsActivity
import com.androiddevs.mvvmnewsapp.util.CountrySelect.COUNTRY_NAME_ISO

class CountryAdapter(private val countries:List<CountryCategory>, private val sharedPreferences: SharedPreferences, private val dialog: AlertDialog, val activity: FragmentActivity?) :RecyclerView.Adapter<CountryAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemCountryBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return countries.size
    }



    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.apply {
           tvCountryName.text=countries[position].name
            imgCountry.setImageResource(countries[position].image)
        }

        holder.itemView. setOnClickListener{
          val editor= sharedPreferences.edit()
            editor.putString(COUNTRY_NAME_ISO,countries[position].countryISo)
            editor.apply()
            dialog.dismiss()
             }
        }
    }






