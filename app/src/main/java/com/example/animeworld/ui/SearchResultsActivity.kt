package com.example.animeworld.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeworld.adapters.AnimeAdapter
import com.example.animeworld.databinding.ActivitySearchResultsBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val queryText = intent.getStringExtra("query text")

        val viewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val searchAnimeLiveData = viewModel.searchAnime(queryText!!)

        searchAnimeLiveData.observe(this) { searchedAnimeList ->
            setRecyclerAdapter(searchedAnimeList)
        }
    }

    private fun setRecyclerAdapter(searchedAnimeList: List<Anime>) {
        val layoutManager = GridLayoutManager(this@SearchResultsActivity, 2)
        val adapter = object : AnimeAdapter(searchedAnimeList,
            this@SearchResultsActivity,
            layoutManager,
            this@SearchResultsActivity) {
            override fun loadNextPage(page: Int) {}
        }

        binding.searchRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        binding.searchProgressBar.visibility = View.GONE
    }
}