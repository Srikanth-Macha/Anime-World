package com.example.animeworld.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeworld.adapters.AnimeAdapter
import com.example.animeworld.databinding.ActivityFavouritesBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel

class FavouritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userEmail = getSharedPreferences("User", Context.MODE_PRIVATE).getString("Email", null)

        val viewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val favouritesLiveData = viewModel.getFavourites(userEmail.toString())

        favouritesLiveData.observe(this) { favouritesList ->
            setRecyclerView(favouritesList)
        }

    }

    private fun setRecyclerView(favouritesList: List<Anime>?) {
        val layoutManager = GridLayoutManager(this@FavouritesActivity, 2)

        val adapter = object : AnimeAdapter(
            favouritesList,
            this@FavouritesActivity,
            layoutManager,
            this@FavouritesActivity
        ) {

            override fun loadNextPage(page: Int) {}
        }

        binding.favouritesRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        binding.lottieLoading.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }
}