package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article
import java.util.zip.Inflater

class ArticlesAdapter (var articles : List<Article>) : Adapter<ArticlesAdapter.ArticleViewHolder>() {
    class ArticleViewHolder(val item: View):ViewHolder(item){
        val image : ImageView = item.findViewById(R.id.articleImage)
        val articleAuthor : TextView = item.findViewById(R.id.articleAuthor)
        val articleTitle : TextView = item.findViewById(R.id.articleTitle)
        val articleDate : TextView = item.findViewById(R.id.articleDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent,false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
       return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles.get(position)
        holder.articleAuthor.text = article?.author
        holder.articleTitle.text = article?.title
        holder.articleDate.text = article?.publishedAt
        Glide.with(holder.item).load(article?.urlToImage).into(holder.image)
    }
    fun changeData(newList : List<Article>){
        articles = newList
        notifyDataSetChanged()
    }
}