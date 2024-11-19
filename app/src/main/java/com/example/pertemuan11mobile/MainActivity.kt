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
import com.example.pertemuan11mobile.database.FavoriteDatabase
import com.example.pertemuan11mobile.fragment.FavoriteFragment
import com.example.pertemuan11mobile.fragments.TopStoriesFragment
import com.example.pertemuan11mobile.model.Article
import com.example.pertemuan11mobile.model.FavoriteArticle
import com.example.pertemuan11mobile.network.ApiClient
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)


        setupTabNavigation()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun setupTabNavigation() {
        tabLayout.addTab(tabLayout.newTab().setText("Top Stories"))
        tabLayout.addTab(tabLayout.newTab().setText("Favorites"))


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, TopStoriesFragment())
            .commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when (tab?.position) {
                    0 -> TopStoriesFragment()  // Tab 0: Top Stories
                    1 -> FavoriteFragment()    // Tab 1: Favorites
                    else -> null
                }
                fragment?.let {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, it)
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}
