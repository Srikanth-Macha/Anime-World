package com.example.animeworld.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeworld.adapters.AnimeAdapter
import com.example.animeworld.databinding.ActivityWatchListBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel

class WatchListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWatchListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Watch List"

        val viewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val userEmail = getSharedPreferences("User", Context.MODE_PRIVATE).getString("Email", null)

        val watchListLiveData = viewModel.getWatchList(userEmail)

        watchListLiveData.observe(this) { watchList ->
            if (watchList.isEmpty()) {
                binding.emptyMessage.isVisible = true
                binding.lottieLoading.visibility = View.GONE
            } else {
                setRecyclerView(watchList)
                binding.emptyMessage.visibility = View.GONE
            }
        }
    }

    private fun setRecyclerView(watchList: List<Anime>?) {
        val layoutManager = GridLayoutManager(this@WatchListActivity, 2)

        val adapter = object : AnimeAdapter(
            watchList,
            this@WatchListActivity,
            layoutManager,
            this@WatchListActivity
        ) {

            override fun loadNextPage(page: Int) {}
        }

        binding.watchListRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        binding.lottieLoading.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}