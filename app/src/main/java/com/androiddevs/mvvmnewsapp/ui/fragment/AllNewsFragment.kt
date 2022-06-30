package com.androiddevs.mvvmnewsapp.ui.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.AllNewsFragmentBinding
import com.androiddevs.mvvmnewsapp.ui.adapter.CategoryAdapter
import com.androiddevs.mvvmnewsapp.ui.adapter.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.viewModel.AllNewsViewModel
import com.androiddevs.mvvmnewsapp.util.CountrySelect.getCountryCode
import com.androiddevs.mvvmnewsapp.util.NewsSelect.newsList
import com.androiddevs.mvvmnewsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllNewsFragment : Fragment() {

    private lateinit var binding: AllNewsFragmentBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: AllNewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    var isLoading = false
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvPageName.text = getString(R.string.home)
            Setting.setOnClickListener {
                findNavController().navigate(R.id.settingFragment)
            }
        }
        setupRecyclerView()
        newsAdapter.notifyDataSetChanged()
        viewModel.getBreakNews(requireContext())
        //observer
        observeNews()
        clickOnArticle()
    }


    //hide and show progressBar
    private fun hideProgressBar() {
        binding.shimmerContainer.visibility = View.GONE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.shimmerContainer.visibility = View.VISIBLE
        isLoading = true
    }

    private fun clickOnArticle() {
        newsAdapter.SetOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.articleFragment, bundle
            )
        }
    }

    private fun observeNews() {
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    hideProgressBar()

                    response.data?.let { newsResponse ->

                        newsAdapter.differ.submitList(newsResponse.articles?.toList())

                    }
                }
                is Resource.Error -> {
                    showProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occured:$message", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }


    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter(newsList(requireContext()))
        binding.recCatagory.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        newsAdapter = NewsAdapter()
        binding.recNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}