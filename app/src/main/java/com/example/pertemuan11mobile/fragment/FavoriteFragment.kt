package com.example.pertemuan11mobile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan11mobile.R
import com.example.pertemuan11mobile.adapter.FavoriteAdapter
import com.example.pertemuan11mobile.database.FavoriteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private lateinit var recyclerViewFavorites: RecyclerView
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        recyclerViewFavorites = view.findViewById(R.id.recyclerViewFavorites)
        recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())

        adapter = FavoriteAdapter(emptyList())
        recyclerViewFavorites.adapter = adapter

        loadFavorites()

        return view
    }

    private fun loadFavorites() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = FavoriteDatabase.getInstance(requireContext())
                val favorites = db.favoriteDao().getAllFavorites()
                CoroutineScope(Dispatchers.Main).launch {
                    adapter.updateData(favorites)
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}