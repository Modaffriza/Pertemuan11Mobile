package com.example.pertemuan11mobile.network

import com.example.pertemuan11mobile.adapter.ArticleResponse
import com.example.pertemuan11mobile.model.Article
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("svc/topstories/v2/home.json")
    fun getTopStories(@Query("api-key") apiKey: String): Call<ArticleResponse>

}