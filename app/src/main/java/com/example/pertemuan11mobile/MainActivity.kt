package com.example.pertemuan11mobile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan11mobile.R
import com.example.pertemuan11mobile.adapter.ArticleAdapter
import com.example.pertemuan11mobile.adapter.ArticleResponse
import com.example.pertemuan11mobile.model.Article
import com.example.pertemuan11mobile.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleAdapter
    private val apiKey = "9PxSnNYoTdMe8WrculQVxFoWcKwU0iOl"  // Replace with your actual API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with an empty list
        adapter = ArticleAdapter(emptyList())
        recyclerView.adapter = adapter

        fetchArticles()
    }

    private fun fetchArticles() {
        val client = ApiClient.getInstance()
        client.getTopStories(apiKey).enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.results ?: emptyList()
                    // Update the adapter with the new list of articles
                    adapter = ArticleAdapter(articles)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Failed to fetch data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
