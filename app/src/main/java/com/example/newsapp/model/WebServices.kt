package com.example.newsapp.model


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("/v2/top-headlines/sources")
    fun getTabs(
        @Query("apiKey") api:String
    ):Call<TabsResponse>

    @GET("/v2/everything")
    fun getArticles(
        @Query("apiKey") api: String,
        @Query("sources") tab: String
    ):Call<ArticlesResponse>
}