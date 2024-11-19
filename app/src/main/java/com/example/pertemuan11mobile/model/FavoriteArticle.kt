package com.example.pertemuan11mobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_articles")
data class FavoriteArticle(
    @PrimaryKey
    val url: String,
    val title: String,
    val byline: String,
    val summary: String,
    val imageUrl: String?


)
