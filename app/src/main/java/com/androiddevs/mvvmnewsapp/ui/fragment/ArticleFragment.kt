package com.androiddevs.mvvmnewsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmnewsapp.databinding.FragmentArticleBinding
import com.androiddevs.mvvmnewsapp.ui.viewModel.NewsViewModel
import com.androiddevs.mvvmnewsapp.ui.viewModel.SavedNewsFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment:Fragment(){

    private lateinit var binding: FragmentArticleBinding
    private val viewModel: SavedNewsFragmentViewModel by viewModels()
    //kotlin create it when give argument in nav_graph thing i want to move and make it serializable
    val args:ArticleFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article
        binding.webView.apply {
            webViewClient= WebViewClient()

            if (article.url != null)
                 loadUrl(article.url)
        }
        binding.fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved Successfully" , Snackbar.LENGTH_SHORT).show()
        }
    }

    }
