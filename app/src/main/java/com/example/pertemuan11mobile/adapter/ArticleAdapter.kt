package com.example.pertemuan11mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pertemuan11mobile.R
import com.example.pertemuan11mobile.model.Article

class ArticleAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val byline: TextView = itemView.findViewById(R.id.tvByline)
        val image: ImageView = itemView.findViewById(R.id.imageView) // Tambahkan ImageView di layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.byline.text = article.byline

        // Cek apakah ada multimedia dan ambil gambar pertama (jika ada)
        val imageUrl = article.multimedia?.firstOrNull()?.url
        if (imageUrl != null) {
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image) // Tambahkan gambar placeholder
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.placeholder_image) // Tampilkan placeholder jika tidak ada gambar
        }
    }

    override fun getItemCount(): Int = articles.size
}
