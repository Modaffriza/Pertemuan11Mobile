package com.example.pertemuan11mobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pertemuan11mobile.R
import com.example.pertemuan11mobile.database.FavoriteDatabase
import com.example.pertemuan11mobile.model.Article
import com.example.pertemuan11mobile.model.FavoriteArticle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleAdapter(
    private var articles: List<Article>,
    private val onFavoriteClick: (Article) -> Unit // Callback function
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount() = articles.size

    // Update articles and refresh the RecyclerView
    fun updateArticles(newArticles: List<Article>) {
        this.articles = newArticles
        notifyDataSetChanged() // Refresh the entire dataset
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvByline: TextView = itemView.findViewById(R.id.tvByline)
        private val btnFavorite: ImageButton = itemView.findViewById(R.id.btnFavorite)

        fun bind(article: Article) {
            tvTitle.text = article.title
            tvByline.text = article.byline

            // Load image
            article.multimedia?.firstOrNull()?.url?.let {
                Glide.with(itemView.context).load(it).into(imageView)
            }

            // Update favorite icon state
            CoroutineScope(Dispatchers.IO).launch {
                val dao = FavoriteDatabase.getInstance(itemView.context).favoriteDao()
                val isFavorited = dao.getFavoriteByUrl(article.url) != null

                itemView.post {
                    btnFavorite.setImageResource(
                        if (isFavorited) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
                    )
                }
            }

            // Handle favorite button click
            btnFavorite.setOnClickListener {
                onFavoriteClick(article) // Call the callback function from MainActivity
            }
        }
    }
}
