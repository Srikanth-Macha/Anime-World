package com.example.animeworld.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeworld.adapters.AnimeAdapter
import com.example.animeworld.databinding.ActivityWatchListBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel

class WatchListActivity : AppCompatActivity() {
    lateinit var binding: ActivityWatchListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val watchListLiveData = viewModel.getWatchList()

        watchListLiveData.observe(this) { watchList ->
            setRecyclerView(watchList)
        }
    }

    private fun setRecyclerView(watchList: List<Anime>?) {
        val layoutManager = GridLayoutManager(this@WatchListActivity, 2)

        val adapter = object : AnimeAdapter(
            watchList,
            this@WatchListActivity,
            layoutManager,
            this@WatchListActivity) {

            override fun loadNextPage(page: Int) {}
        }

        binding.watchListRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        binding.progressBar.visibility = View.GONE
    }
}