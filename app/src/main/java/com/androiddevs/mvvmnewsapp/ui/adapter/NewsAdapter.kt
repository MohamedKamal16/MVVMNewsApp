package com.androiddevs.mvvmnewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.databinding.ItemArticlePreviewBinding
import com.androiddevs.mvvmnewsapp.model.dataClass.Article
import com.bumptech.glide.Glide

class NewsAdapter() :RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding:ItemArticlePreviewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
     val article=differ.currentList[position]

        holder.binding.apply {

        /*    if (article.urlToImage == null){
               ivArticleImage.setImageResource(R.drawable.ic_baseline_error_24)
            }*/
            Glide.with(ivArticleImage.context)
                .load(article.urlToImage)
                .error(R.drawable.ic_baseline_error_24)
                .into(ivArticleImage)

            tvSource.text=article.source?.name
            tvTitle.text=article.title
            tvPublishedAt.text=article.publishedAt
        }

        holder.itemView. setOnClickListener{
            onItemClickListener?.let { it(article) }
        }
    }
    //not understand it its for click on view
    private var onItemClickListener:((Article)->Unit)?=null
    fun SetOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener=listener
    }



    // util to see only change in articles to refresh it only not like we give list and we refresh all list
    private val differCallBack = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article,newItem:Article):Boolean{
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    //Async list differ take two list and compare them to change the difference only it run on background
    val differ=AsyncListDiffer(this,differCallBack)


}