package com.example.animeworld.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.animeworld.R
import com.example.animeworld.adapters.AnimeSourcesAdapter
import com.example.animeworld.adapters.SimilarAnimeAdapter
import com.example.animeworld.databinding.ActivityAnimeInfoBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel
import java.lang.Integer.min

class AnimeInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimeInfoBinding
    private lateinit var viewModel: AnimeViewModel
    private var activityName: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activityName = intent.getStringExtra("activity name")
        disableBackgroundInteraction() // when progress bar is loading

        val anime: Anime = intent.getSerializableExtra("anime info") as Anime
        viewModel = ViewModelProvider(this)[AnimeViewModel::class.java]

        showFloatingButtons() // Show floating button according to previous intent
        val animeTags: List<String> = listOf(anime.tags?.get(0) ?: "", anime.tags?.get(1) ?: "")

        getSimilarAnimeList(animeTags) // To get list of similar anime

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

            val tagsList = anime.tags

            // Creating textViews of all the words in tagsList
            for (i in 0 until min(tagsList?.size!!, 20)) {
                val textView = TextView(this@AnimeInfoActivity)
                val tagName = tagsList[i]

                textView.apply {
                    this.textSize = 15f
                    text = tagName
                    typeface = Typeface.DEFAULT_BOLD
                    foreground = ContextCompat.getDrawable(
                        this@AnimeInfoActivity,
                        R.drawable.anime_tags_background
                    )
                    setPadding(25, 12, 25, 15)

                    // To handle clicking on tags
                    setOnClickListener {
                        val intent =
                            Intent(this@AnimeInfoActivity, SearchResultsActivity::class.java)
                                .putExtra("query text", "tag:${tagName}")

                        startActivity(intent)
                    }
                }

                binding.animeTagsLayout.addView(textView)
            }

        }

        Glide.with(this).load(anime.picture).into(binding.animeImage)

        addAnimeToWatchList(anime)
        addAnimeToFavourites(anime)

        removeAnime(anime)
    }

    private fun getSimilarAnimeList(animeTags: List<String>?) {
        val similarAnimeLiveData = viewModel.similarAnime(animeTags)

        similarAnimeLiveData.observe(this) {
            addSimilarAnimeToUI(it)
            Log.e("getSimilarAnimeList", it.toString())
            binding.similarAnimeProgressBar.visibility = View.GONE
        }
    }

    private fun addSimilarAnimeToUI(similarAnimeList: List<Anime>?) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.similarAnimeRecyclerView.apply {
            adapter = SimilarAnimeAdapter(
                this@AnimeInfoActivity, similarAnimeList ?: emptyList()
            )
            this.layoutManager = layoutManager
        }
    }

    private fun removeAnime(anime: Anime) {
        binding.removeButton.setOnClickListener {
            if (activityName?.contains("WatchListActivity") == true) {
                viewModel.removeFromWatchList(anime.title!!, anime.userEmail!!)

                Toast.makeText(
                    this@AnimeInfoActivity,
                    "Removed ${anime.title} from watchlist",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                viewModel.removeFromFavourites(anime.title!!, anime.userEmail!!)

                Toast.makeText(
                    this@AnimeInfoActivity,
                    "Removed ${anime.title} from favourites",
                    Toast.LENGTH_SHORT
                ).show()
            }

            startActivity(Intent(this@AnimeInfoActivity, MainScreenActivity::class.java))
            finishAffinity()
        }
    }

    private fun showFloatingButtons() {
        if (activityName?.contains("MainScreenActivity") == true ||
            activityName?.contains("SearchResultsActivity") == true ||
            activityName?.contains("CategorizedAnimeActivity") == true ||
            activityName?.contains("AnimeInfoActivity") == true
        ) {
            binding.removeButton.visibility = View.GONE
        } else {
            if (activityName?.contains("WatchListActivity") == true) {
                binding.addToWatchList.visibility = View.GONE
            } else if (activityName?.contains("FavouritesActivity") == true) {
                binding.addToFavourites.visibility = View.GONE
            }
        }
    }

    private fun addAnimeToWatchList(anime: Anime) {
        binding.addToWatchList.setOnClickListener {
            val preferences = getSharedPreferences("User", Context.MODE_PRIVATE)
            anime.userEmail = preferences.getString("Email", null)

            viewModel.addAnimeToWatchList(anime)

            Toast.makeText(this, "Adding to the Watchlist . . .", Toast.LENGTH_SHORT).show()
        }

        binding.addToWatchList.setOnLongClickListener {
            Toast.makeText(
                this@AnimeInfoActivity,
                "Add this anime to WatchList",
                Toast.LENGTH_SHORT
            ).show()

            return@setOnLongClickListener true
        }
    }

    private fun addAnimeToFavourites(anime: Anime) {
        binding.addToFavourites.setOnClickListener {
            val preferences = getSharedPreferences("User", Context.MODE_PRIVATE)
            anime.userEmail = preferences.getString("Email", null)

            viewModel.addAnimeToFavourites(anime)

            Toast.makeText(this, "Adding to the Favourites . . .", Toast.LENGTH_SHORT).show()
        }

        binding.addToFavourites.setOnLongClickListener {
            Toast.makeText(
                this@AnimeInfoActivity,
                "Add this anime to Favourites",
                Toast.LENGTH_SHORT
            ).show()

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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
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