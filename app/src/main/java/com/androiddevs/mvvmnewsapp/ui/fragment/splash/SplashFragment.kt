package com.androiddevs.mvvmnewsapp.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.util.Constant.First_TIME
import com.androiddevs.mvvmnewsapp.util.LocaleHelper.getSharedPreference

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val handler=Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (firstTime()){
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_allNewsFragment)

            }

        }, 2000)
    }

    private fun firstTime(): Boolean {
        val sharedPref =getSharedPreference(requireContext())
        return sharedPref.getBoolean(First_TIME, true)
    }


}