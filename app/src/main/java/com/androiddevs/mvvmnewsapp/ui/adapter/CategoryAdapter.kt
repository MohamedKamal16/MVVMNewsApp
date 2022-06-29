package com.androiddevs.mvvmnewsapp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.ItemCountryBinding
import com.androiddevs.mvvmnewsapp.databinding.ItemNewsCategoryBinding
import com.androiddevs.mvvmnewsapp.model.dataClass.CountryCategory
import com.androiddevs.mvvmnewsapp.model.dataClass.NewsCategory
import com.androiddevs.mvvmnewsapp.util.CountrySelect.COUNTRY_NAME_ISO
import com.androiddevs.mvvmnewsapp.util.NewsSelect.NEWS_Category

class CategoryAdapter(val category: List<NewsCategory>) :
    RecyclerView.Adapter<CategoryAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemNewsCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemNewsCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return category.size
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.apply {
            tvTitle.text = category[position].name
            ivArticleImage.setImageResource(category[position].image)
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.apply {
                putString(NEWS_Category, category[position].nameIso)
            }
            holder.itemView.findNavController().navigate(R.id.action_allNewsFragment_to_homePage, bundle)
        }
    }
}






