package com.example.newsapp.ui.home.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article


class ArticleDetailsFragment(var article: Article ) : Fragment() {
    lateinit var articleImg : ImageView
    lateinit var articleSource : TextView
    lateinit var articleTitle : TextView
    lateinit var articlePostTime : TextView
    lateinit var articleParagraph : TextView
    lateinit var articleLink : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()
    }

    fun initViews(view: View){
        articleImg = view.findViewById(R.id.articleDetailImg)
        Glide.with(articleImg).load(article!!.urlToImage).into(articleImg)
        articleSource = view.findViewById(R.id.articleSource)
        articleSource.setText(article.author)
        articleTitle = view.findViewById(R.id.articleDetailTitle)
        articleTitle.setText(article.title)
        articlePostTime = view.findViewById(R.id.articlePostTime)
        articlePostTime.setText(article.publishedAt)
        articleParagraph = view.findViewById(R.id.articleParagraph)
        articleParagraph.setText(article.content)
        articleLink = view.findViewById(R.id.articleLink)
    }
    fun initListeners(){
        articleLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(browserIntent)

        }
    }


}