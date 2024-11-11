package com.example.pertemuan11mobile.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("title") val title: String,
    @SerializedName("byline") val byline: String,
    @SerializedName("abstract") val summary: String,
    @SerializedName("url") val url: String,
    @SerializedName("multimedia") val multimedia: List<Multimedia>? // Multimedia list, nullable
)

data class Multimedia(
    @SerializedName("url") val url: String,
    @SerializedName("format") val format: String,
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("type") val type: String,
    @SerializedName("subtype") val subtype: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("copyright") val copyright: String
)
