package com.example.pertemuan11mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pertemuan11mobile.R
import com.example.pertemuan11mobile.model.FavoriteArticle

class FavoriteAdapter(private var favorites: List<FavoriteArticle>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.bind(favorite)
    }

    override fun getItemCount(): Int = favorites.size

    fun updateData(newFavorites: List<FavoriteArticle>) {
        this.favorites = newFavorites
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvByline: TextView = itemView.findViewById(R.id.tvByline)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)


        fun bind(favorite: FavoriteArticle) {
            tvTitle.text = favorite.title
            tvByline.text = favorite.byline

            if (!favorite.imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(favorite.imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.placeholder_image)
            }
        }
    }
}