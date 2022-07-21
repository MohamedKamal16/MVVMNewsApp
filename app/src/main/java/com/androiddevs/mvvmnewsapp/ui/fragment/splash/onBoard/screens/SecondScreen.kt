package com.androiddevs.mvvmnewsapp.ui.fragment.splash.onBoard.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.SecondScreenBinding
import com.androiddevs.mvvmnewsapp.ui.activity.NewsActivity
import com.androiddevs.mvvmnewsapp.util.Constant
import com.androiddevs.mvvmnewsapp.util.CountrySelect.COUNTRY_NAME_ISO
import com.androiddevs.mvvmnewsapp.util.LocaleHelper
import com.androiddevs.mvvmnewsapp.util.LocaleHelper.CHOOSE_LANGUAGE
import com.androiddevs.mvvmnewsapp.util.LocaleHelper.getSharedPreference


class SecondScreen : Fragment() {

    private lateinit var binding: SecondScreenBinding
    private lateinit var language:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.tvNext2.setOnClickListener {
            firstTimeChoice()
            findNavController().navigate(R.id.action_viewPagerFragment_to_allNewsFragment)
            LocaleHelper.setLocale(requireContext(), language)
            (activity as NewsActivity).recreate()
        }
    }

    private fun firstTimeChoice() {
        with(binding) {
             language = when (radioGroup.checkedRadioButtonId) {
                R.id.rb_en -> "en"
                R.id.rb_ar -> "ar"
                else -> "en"
            }
            val country = when (rg2.checkedRadioButtonId) {
                R.id.rb_eg -> "eg"
                R.id.rb_usa  -> "us"
                else -> "us"
            }
            Log.w("Check",language)
            Log.w("Check",""+radioGroup.checkedRadioButtonId)

            Log.w("Check",country)
            Log.w("Check",""+rg2.checkedRadioButtonId)

            val preferenceEdit = getSharedPreference(requireContext()).edit()
            preferenceEdit.putString(CHOOSE_LANGUAGE, language)
            preferenceEdit.putString(COUNTRY_NAME_ISO, country)
            preferenceEdit.putBoolean(Constant.First_TIME, false)
            preferenceEdit.apply()
        }

    }


}