package com.example.animeworld.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.animeworld.databinding.ActivityAnimeInfoBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel

class AnimeInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val anime: Anime = intent.getSerializableExtra("anime info") as Anime

        val viewModel: AnimeViewModel = ViewModelProvider(this)[AnimeViewModel::class.java]

        val malScraperLiveData = viewModel.getDataFromMalScraper(anime.title.toString())

        malScraperLiveData.observe(this) { animeResponse ->
            Toast.makeText(this@AnimeInfoActivity, animeResponse.toString(), Toast.LENGTH_SHORT)
                .show()
        }

        binding.apply {
            animeTitle.text = anime.title
            animeEpisodes.text = "Episodes: ${anime.episodes.toString()}"
            animeType.text = "Type: ${anime.type}"
            animeStatus.text = "Status: ${anime.status}"
            animeSeason.text = "Anime Season: ${anime.animeSeason.toString()}"
            tags.text = setUpTextView(anime.tags.toString())
            sources.text = setUpTextView(anime.sources.toString())
            relations.text = setUpTextView(anime.relations.toString())
        }

        Glide.with(this).load(anime.picture).into(binding.animeImage)

    }

    private fun setUpTextView(string: String): String {
        val arr = string.trim('[', ']').split(',')
        var newString = ""

        for (s in arr) {
            newString += s
            newString += '\n'
        }

        return newString
    }
}