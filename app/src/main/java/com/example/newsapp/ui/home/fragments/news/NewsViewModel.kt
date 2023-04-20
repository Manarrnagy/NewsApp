package com.example.newsapp.ui.home.fragments.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.AppConstants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.Article
import com.example.newsapp.model.ArticlesResponse
import com.example.newsapp.model.TabDM
import com.example.newsapp.model.TabsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    var tabsLiveData = MutableLiveData<List<TabDM?>?>()
    var tabsLiveDataStatus = MutableLiveData<Boolean>()
    var articlesLiveData = MutableLiveData<List<Article>>()
    var articlesLiveDataStatus = MutableLiveData<Boolean>()
    var progressBarLiveData = MutableLiveData<Boolean>()

    fun getTabs(categoryId: String){
        ApiManager.getApis().getTabs(AppConstants.apiKey,categoryId )
            .enqueue(object : Callback<TabsResponse> {
                override fun onResponse(
                    call: Call<TabsResponse>,
                    response: Response<TabsResponse>
                ) {
                    progressBarLiveData.value = false
                    tabsLiveDataStatus.value= true
                    if(response.code() in 200..299){
                        tabsLiveData.value = response.body()?.tabs
                    }
                }
                override fun onFailure(call: Call<TabsResponse>, t: Throwable) {
                    progressBarLiveData.value = false
                    tabsLiveDataStatus.value= false
                    Log.e("ON FALIURE ##","$t")
                }
            })
    }

    public fun getArticles(tabId :String, pageSize :Int , page : Int) {
        progressBarLiveData.value = true

        var isLoading = false
        ApiManager.getApis().getArticles(AppConstants.apiKey,tabId, pageSize, page).enqueue(object :Callback<ArticlesResponse>{
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if(response.body()?.articles != null) {
                    progressBarLiveData.value = false
                    articlesLiveDataStatus.value= true
                    var totalResults = response.body()?.totalResults!!
                    articlesLiveData.value = response.body()?.articles as List<Article>
                }

            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                progressBarLiveData.value = false
                articlesLiveDataStatus.value= false
                Log.e("On FALIURE ##","$t")
            }
        })
    }
}