package com.example.newsapp.ui.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.AppConstants
import com.example.newsapp.R
import com.example.newsapp.adapter.ArticlesAdapter
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment(val category : Category) : Fragment() {
    lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    var adapter: ArticlesAdapter =ArticlesAdapter(listOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListners()
        getTabs()
    }
    fun getTabs(){
        ApiManager.getApis().getTabs(AppConstants.apiKey,category.id )
            .enqueue(object : Callback<TabsResponse> {
                override fun onResponse(
                    call: Call<TabsResponse>,
                    response: Response<TabsResponse>
                ) {
                    progressBar.isVisible = false
                    Log.e("ON RESPONSE ##","${response.body()}")
                    if(response.body()?.code == null){
                        showTabs(response.body()?.tabs as List<TabDM>)
                    }

                }

                override fun onFailure(call: Call<TabsResponse>, t: Throwable) {
                    progressBar.isVisible = false
                    Log.e("ON FALIURE ##","$t")
                   Toast.makeText(requireContext(),"Something went wrong!",Toast.LENGTH_LONG).show()
                }
            })
    }
    fun initViews(view: View){
        tabLayout = view.findViewById(R.id.tabs)
        progressBar = view.findViewById(R.id.progressBar)
        recyclerView = view.findViewById(R.id.articleRV)
        recyclerView.adapter = adapter
        adapter.onArticleClick = object : ArticlesAdapter.OnArticleClick{
            override fun onArticleClick(article: Article, position: Int) {
              //  onArticleClick?.OnArcticleClick(article)

                pushFragment(ArticleDetailsFragment(article))
            }

        }
    }

    fun pushFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, fragment)?.commit()
    }

    fun showTabs(tabs: List<TabDM>){
        tabs.forEach {
            val newTab = tabLayout.newTab()
            newTab.text = it?.name
            newTab.tag = it.id?: ""
            tabLayout.addTab(newTab)
        }
    }
    fun initListners(){
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val id = tab?.tag!! as String
                getArticles(id)
            }


            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })
    }
     fun getArticles(tabId :String) {
        progressBar.isVisible = true
         ApiManager.getApis().getArticles(AppConstants.apiKey,tabId).enqueue(object :Callback<ArticlesResponse>{
             override fun onResponse(
                 call: Call<ArticlesResponse>,
                 response: Response<ArticlesResponse>
             ) {
                 progressBar.isVisible = false
                 //Log.e("On Response", "${response.body()?.articles}")
                 adapter.changeData(response.body()?.articles as List<Article>)
             }

             override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                 progressBar.isVisible = false
                 Log.e("ON FALIURE ##","$t")
                 Toast.makeText(requireContext(),"Something went wrong!",Toast.LENGTH_LONG).show()
             }

         })
    }
//    var onArticleClick:OnArcticleClick? = null
//    interface OnArcticleClick{
//        fun OnArcticleClick(article: Article)
//    }
}