package com.example.animeworld.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeworld.adapters.AnimeAdapter
import com.example.animeworld.databinding.ActivityCategorizedAnimeBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel

class CategorizedAnimeActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategorizedAnimeBinding

    private var currentPage: Number = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorizedAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra("categoryName")

        supportActionBar?.apply {
            title = categoryName
            setDisplayHomeAsUpEnabled(true)
        }

        val viewModel: AnimeViewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val categorizedAnimeLiveData =
            viewModel.getCategorizedAnime(categoryName.toString(), currentPage)

        categorizedAnimeLiveData.observe(this) { categorizedAnimeList ->
            setUpRecyclerView(
                viewModel,
                categorizedAnimeList as MutableList<Anime>?,
                categoryName.toString()
            )
        }
    }

    private fun setUpRecyclerView(
        viewModel: AnimeViewModel,
        categorizedAnimeList: MutableList<Anime>?,
        categoryName: String,
    ) {
        val gridLayoutManager = GridLayoutManager(this, 2)
        val adapter = object : AnimeAdapter(categorizedAnimeList,
            this@CategorizedAnimeActivity,
            gridLayoutManager,
            this@CategorizedAnimeActivity) {

            @SuppressLint("NotifyDataSetChanged")
            override fun loadNextPage(page: Int) {
                binding.lottieLoading.visibility = View.VISIBLE
                val categorizedAnimeLiveData = viewModel.getCategorizedAnime(categoryName, page)

                categorizedAnimeLiveData.observe(this@CategorizedAnimeActivity) { newAnimeList ->
                    categorizedAnimeList?.addAll(newAnimeList)
                    notifyDataSetChanged()

                    binding.lottieLoading.visibility = View.GONE
                }

            }
        }

        binding.categoryRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = gridLayoutManager
        }

        binding.lottieLoading.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}