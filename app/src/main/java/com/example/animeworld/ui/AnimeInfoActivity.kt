package com.example.animeworld.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animeworld.adapters.AnimeSourcesAdapter
import com.example.animeworld.databinding.ActivityAnimeInfoBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel

class AnimeInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeInfoBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        disableBackgroundInteraction() // when progress bar is loading

        val anime: Anime = intent.getSerializableExtra("anime info") as Anime

        showFloatingButtons() // Show floating button according to previous intent

        val viewModel: AnimeViewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val malScraperLiveData = viewModel.getDataFromMalScraper(anime.title.toString())

        malScraperLiveData.observe(this) { animeResponse ->
            if (animeResponse.score == null && animeResponse.description == null) {
                anime.apply {
                    score = "_"
                    rating = "_"
                    startDate = "- "
                    endDate = " -"
                    description = "No description available"
                }
            } else {
                anime.apply {
                    score = animeResponse.score
                    rating = animeResponse.rating
                    startDate = animeResponse.startDate
                    endDate = animeResponse.endDate
                    description = animeResponse.description
                }
            }

            updateAnimeDetails(anime)
        }

        binding.apply {
            animeTitle.text = anime.title
            animeEpisodes.text = "Episodes: ${anime.episodes.toString()}"
            animeType.text = "Type: ${anime.type}"
            animeStatus.text = "Status: ${anime.status}"

            var tags = ""
            val tagsList = anime.tags

            for (i in 0 until tagsList?.size!!) {
                tags += "${tagsList[i]}\t\t\t\t\t"

                if (i == 10)
                    break
            }

            animeTags.text = tags
        }

        Glide.with(this).load(anime.picture).into(binding.animeImage)

        addAnimeToWatchList(viewModel, anime)
        addAnimeToFavourites(viewModel, anime)
    }

    private fun showFloatingButtons() {
        val activityName = intent.getStringExtra("activity name")

        if (activityName?.contains("MainScreenActivity") == true) {
            binding.removeButton.visibility = View.GONE
        }
        else {
            if (activityName?.contains("WatchListActivity") == true) {
                binding.addToWatchList.visibility = View.GONE
            }
            else if (activityName?.contains("FavouritesActivity") == true) {
                binding.addToFavourites.visibility = View.GONE
            }

            binding.removeButton.setOnClickListener {
                Toast.makeText(this@AnimeInfoActivity, "remove", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addAnimeToWatchList(viewModel: AnimeViewModel, anime: Anime) {
        binding.addToWatchList.setOnClickListener {
            val preferences = getSharedPreferences("User", Context.MODE_PRIVATE)
            anime.userEmail = preferences.getString("Email", null)

            viewModel.addAnimeToWatchList(anime)

            Toast.makeText(this, "Adding to the Watchlist . . .", Toast.LENGTH_SHORT).show()
        }

        binding.addToWatchList.setOnLongClickListener {
            Toast.makeText(this@AnimeInfoActivity,
                "Add this anime to WatchList",
                Toast.LENGTH_SHORT).show()

            return@setOnLongClickListener true
        }
    }

    private fun addAnimeToFavourites(viewModel: AnimeViewModel, anime: Anime) {
        binding.addToFavourites.setOnClickListener {
            val preferences = getSharedPreferences("User", Context.MODE_PRIVATE)
            anime.userEmail = preferences.getString("Email", null)

            viewModel.addAnimeToFavourites(anime)

            Toast.makeText(this, "Adding to the Favourites . . .", Toast.LENGTH_SHORT).show()
        }

        binding.addToFavourites.setOnLongClickListener {
            Toast.makeText(this@AnimeInfoActivity,
                "Add this anime to Favourites",
                Toast.LENGTH_SHORT).show()

            return@setOnLongClickListener true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateAnimeDetails(anime: Anime) {
        binding.apply {
            animeScore.text = animeScore.text.toString() + anime.score
            animeRating.text = animeRating.text.toString() + anime.rating
            airingDates.text =
                airingDates.text.toString() + anime.startDate + " to " + anime.endDate
            description.text = anime.description

            anime.sources?.let { setSourcesRecyclerView(it) }

            lottieLoading.visibility = View.GONE
            enableBackgroundInteraction()
        }
    }

    private fun disableBackgroundInteraction() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun enableBackgroundInteraction() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        binding.blurLayout.visibility = View.GONE
    }

    // for scrollable list of source links of Anime to watch
    private fun setSourcesRecyclerView(sources: List<String>) {
        binding.animeSourcesRecyclerView.apply {
            adapter = AnimeSourcesAdapter(this@AnimeInfoActivity, sources)
            layoutManager = object : LinearLayoutManager(this@AnimeInfoActivity) {
                override fun canScrollVertically(): Boolean = false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}