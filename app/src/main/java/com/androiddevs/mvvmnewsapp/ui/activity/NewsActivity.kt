package com.androiddevs.mvvmnewsapp.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.ActivityNewsBinding
import com.androiddevs.mvvmnewsapp.ui.viewModel.SettingViewModel
import com.androiddevs.mvvmnewsapp.util.LocaleHelper.onAttach
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var navController: NavController

    private val viewModel: SettingViewModel by viewModels()
    lateinit var navView: BottomNavigationView
    var index=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navView = binding.navView
        navBind()
    }



    private fun navBind() {

        navController = findNavController(R.id.newsNavHostFragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.articleFragment
                ||destination.id == R.id.settingFragment
                ||destination.id==R.id.breakingNewsFragment
                ||destination.id==R.id.splashFragment
                ||destination.id==R.id.viewPagerFragment) {
                binding.navView.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }

        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { onAttach(it) })

    }
}
