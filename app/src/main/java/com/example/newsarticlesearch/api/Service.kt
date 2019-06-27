package com.example.newsarticlesearch.api

import com.example.newsarticlesearch.BuildConfig
import com.example.newsarticlesearch.models.ArticleSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Service {

    @GET("articlesearch.json")
    fun getApiNews(
        @QueryMap parameters : Map<String, String> ,
        @Query("api-key") api_key : String = BuildConfig.API_KEY
    ) : Call<ArticleSearch>


}