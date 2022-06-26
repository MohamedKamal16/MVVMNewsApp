package com.androiddevs.mvvmnewsapp.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.ActivityNewsBinding
import com.androiddevs.mvvmnewsapp.util.Constant
import com.androiddevs.mvvmnewsapp.util.LanguageConfig
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var navController: NavController
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    var index=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navBind()

        index=sharedPreferences.getInt(Constant.CHOOSE_LANGUAGE,0)
    }

    override fun onResume() {
        super.onResume()

       // deafultLanguage()  TODO KEEP Setting Choice
    }

   /* private fun deafultLanguage() {
        if (index == 0) {
            LanguageConfig.setLocate(this, "en")
        } else if (index == 1) {
            LanguageConfig.setLocate(this, "ar")
        }
    }*/


    private fun navBind() {
        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.newsNavHostFragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.articleFragment) {
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

}
