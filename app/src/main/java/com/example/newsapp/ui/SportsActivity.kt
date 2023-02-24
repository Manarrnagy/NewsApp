package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.AppConstants
import com.example.newsapp.R
import com.example.newsapp.adapter.ArticlesAdapter
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.Article
import com.example.newsapp.model.ArticlesResponse
import com.example.newsapp.model.TabDM
import com.example.newsapp.model.TabsResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SportsActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    var adapter:ArticlesAdapter =ArticlesAdapter(listOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViews()
        initListners()
        getTabs()
    }
    fun getTabs(){
        ApiManager.getApis().getTabs(AppConstants.apiKey)
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
                   Toast.makeText(this@SportsActivity,"Something went wrong!",Toast.LENGTH_LONG).show()
                }
            })
    }
    fun initViews(){
        tabLayout = findViewById(R.id.tabs)
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.articleRV)
        recyclerView.adapter = adapter
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
                 Toast.makeText(this@SportsActivity,"Something went wrong!",Toast.LENGTH_LONG).show()
             }

         })
    }
}