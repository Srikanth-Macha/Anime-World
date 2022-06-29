package com.example.animeworld.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
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

        disableBackgroundInteraction()

        val anime: Anime = intent.getSerializableExtra("anime info") as Anime

        val viewModel: AnimeViewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val malScraperLiveData = viewModel.getDataFromMalScraper(anime.title.toString())

        malScraperLiveData.observe(this) { animeResponse ->
            binding.apply {
                animeScore.text = animeScore.text.toString() + animeResponse.score
                animeRating.text = animeRating.text.toString() + animeResponse.rating
                airingDates.text =
                    airingDates.text.toString() + animeResponse.startDate + " to " + animeResponse.endDate
                description.text = animeResponse.description

                anime.sources?.let { setSourcesRecyclerView(it) }

                animeProgressBar.visibility = View.GONE
                enableBackgroundInteraction()
            }
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
    }

    private fun disableBackgroundInteraction() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun enableBackgroundInteraction() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun setSourcesRecyclerView(sources: List<String>) {
        binding.animeSourcesRecyclerView.apply {
            adapter = AnimeSourcesAdapter(this@AnimeInfoActivity, sources)
            layoutManager = object : LinearLayoutManager(this@AnimeInfoActivity) {
                override fun canScrollVertically(): Boolean = false
            }
        }
    }
}