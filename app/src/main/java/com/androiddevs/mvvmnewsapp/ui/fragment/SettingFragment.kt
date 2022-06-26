package com.androiddevs.mvvmnewsapp.ui.fragment

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.SettingFragmentBinding
import com.androiddevs.mvvmnewsapp.ui.activity.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.adapter.CountryAdapter
import com.androiddevs.mvvmnewsapp.ui.adapter.NewsAdapter
import com.androiddevs.mvvmnewsapp.util.Constant.CHOOSE_LANGUAGE
import com.androiddevs.mvvmnewsapp.util.Constant.countryList
import com.androiddevs.mvvmnewsapp.util.LanguageConfig.setLocate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : Fragment() {

    companion object {
        fun newInstance() = SettingFragment()
    }

    private lateinit var binding: SettingFragmentBinding
    var selectedLanguageIndex = -1
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var rec: RecyclerView
    private lateinit var alertDialog: AlertDialog


    @Inject
    lateinit var sharedPreferences:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLanguage.setOnClickListener {
            showChangeLang()
        }
        binding.btnCatagory.setOnClickListener {
            chooseCountry()
        }
    }

    private fun chooseCountry() {
        val builder = AlertDialog.Builder(requireContext())
        val layoutView = layoutInflater.inflate(R.layout.country_dialog, null)
         rec=layoutView.findViewById(R.id.recCountry)
        builder.setView(layoutView)
        alertDialog=builder.create()
        setupRecyclerView(alertDialog)
        alertDialog.show()
    }

    private fun setupRecyclerView(dialog: AlertDialog) {
        countryAdapter = CountryAdapter(countryList(),sharedPreferences, dialog)
        rec.apply {
            adapter = countryAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showChangeLang() {
        val listItems = arrayOf("English","عربي")

        selectedLanguageIndex = sharedPreferences.getInt(CHOOSE_LANGUAGE,0)
        Log.i("setting language", "showChangeLang: $selectedLanguageIndex")

        val mBuilder = AlertDialog.Builder(requireContext())
        mBuilder.setTitle(getString(R.string.chooselanguage))

        mBuilder.setSingleChoiceItems(listItems, selectedLanguageIndex) { dialog, index ->
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(CHOOSE_LANGUAGE, index)
            editor.apply()
         if (index == 0) {
                setLocate(requireContext(),"en")
             (activity as NewsActivity).recreate()
         } else if (index == 1) {
                setLocate(requireContext(),"ar")
             (activity as NewsActivity).recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

}