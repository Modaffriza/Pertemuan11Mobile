package com.example.pertemuan11mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan11mobile.R
import com.example.pertemuan11mobile.adapter.ArticleAdapter
import com.example.pertemuan11mobile.adapter.ArticleResponse
import com.example.pertemuan11mobile.database.FavoriteDatabase
import com.example.pertemuan11mobile.model.Article
import com.example.pertemuan11mobile.model.FavoriteArticle
import com.example.pertemuan11mobile.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopStoriesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleAdapter
    private val apiKey = "9PxSnNYoTdMe8WrculQVxFoWcKwU0iOl"  // Ganti dengan API key Anda

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_top_stories, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewTopStories)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ArticleAdapter(emptyList(), this::onFavoriteClicked)
        recyclerView.adapter = adapter

        fetchArticles()
        return view
    }

    private fun fetchArticles() {
        val client = ApiClient.getInstance()
        client.getTopStories(apiKey).enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.results ?: emptyList()
                    adapter.updateArticles(articles)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onFavoriteClicked(article: Article) {
        val favoriteArticle = FavoriteArticle(
            url = article.url,
            title = article.title,
            byline = article.byline,
            summary = article.summary,
            imageUrl = article.multimedia?.find { it.format == "Super Jumbo" }?.url ?:""

        )


        CoroutineScope(Dispatchers.IO).launch {
            val favoriteDao = FavoriteDatabase.getInstance(requireContext()).favoriteDao()
            favoriteDao.insertFavorite(favoriteArticle)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(requireContext(), "Article added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
