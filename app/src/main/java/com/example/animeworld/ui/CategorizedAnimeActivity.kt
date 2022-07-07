package com.example.animeworld.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeworld.adapters.AnimeAdapter
import com.example.animeworld.databinding.ActivityCategorizedAnimeBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel

class CategorizedAnimeActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategorizedAnimeBinding

    // TODO add-load next page feature

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorizedAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra("categoryName")
        Toast.makeText(this, "$categoryName Anime", Toast.LENGTH_SHORT).show()

        val viewModel: AnimeViewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val categorizedAnimeLiveData = viewModel.getCategorizedAnime(categoryName.toString())

        categorizedAnimeLiveData.observe(this) { categorizedAnimeList ->
            setUpRecyclerView(categorizedAnimeList)
        }

    }

    private fun setUpRecyclerView(categorizedAnimeList: List<Anime>?) {
        val gridLayoutManager = GridLayoutManager(this, 2)
        val adapter = object : AnimeAdapter(categorizedAnimeList,
            this@CategorizedAnimeActivity,
            gridLayoutManager,
            this@CategorizedAnimeActivity) {
            override fun loadNextPage(page: Int) {}
        }

        binding.categoryRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = gridLayoutManager
        }

        binding.categoriesProgressBar.visibility = View.GONE
    }
}