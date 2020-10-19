package com.moter.tutorial.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moter.tutorial.R
import com.moter.tutorial.model.Article
import com.moter.tutorial.model.NewsResponse
import kotlinx.android.synthetic.main.custom_news_item.view.*


class NewsAdapter : PagingDataAdapter<Article, NewsAdapter.NewsItemViewHolder>(DataDifferntiator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_news_item, parent, false)
        return NewsItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class NewsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article?) {
            itemView.tvArticleTitle.text = article?.title
        }
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}