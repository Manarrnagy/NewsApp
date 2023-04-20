package com.example.newsapp.model


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("/v2/top-headlines/sources")
    fun getTabs(
        @Query("apiKey") api:String,
        @Query("category") categoryId : String
    ):Call<TabsResponse>

    @GET("/v2/everything")
    fun getArticles(
        @Query("apiKey") api: String,
        @Query("sources") tab: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int = 1

    ):Call<ArticlesResponse>

    @GET("/v2/everything")
    fun getSearchResults(
        @Query("apiKey") api: String,
        @Query("q") q: String,
        @Query("page") page: Int =1
    ):Call<ArticlesResponse>
}