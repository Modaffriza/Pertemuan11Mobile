package com.example.pertemuan11mobile.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pertemuan11mobile.model.FavoriteArticle

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_articles")
    fun getAllFavorites(): List<FavoriteArticle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(article: FavoriteArticle)

    @Delete
    fun deleteFavorite(article: FavoriteArticle)


    @Query("SELECT * FROM favorite_articles WHERE url = :url LIMIT 1")
    fun getFavoriteByUrl(url: String): FavoriteArticle?
}