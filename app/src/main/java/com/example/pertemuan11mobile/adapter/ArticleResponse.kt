package com.example.pertemuan11mobile.adapter

import com.example.pertemuan11mobile.model.Article
import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("status") val status: String,
    @SerializedName("results") val results: List<Article>
)

