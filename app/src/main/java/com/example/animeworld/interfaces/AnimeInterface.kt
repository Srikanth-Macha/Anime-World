package com.example.animeworld.interfaces

import com.example.animeworld.models.Anime
import com.example.animeworld.retrofit.MyRetrofit
import com.example.animeworld.retrofit.RetrofitAnimeInterface

object AnimeInterface {
    private val retrofitAnimeInterface =
        MyRetrofit.getInstance().create(RetrofitAnimeInterface::class.java)

    suspend fun getAnimeData(pageNumber: Int): List<Anime>? {
        val animeResponse = retrofitAnimeInterface.getAnimeData(pageNumber)

        return animeResponse.body()
    }

    suspend fun searchAnimeByName(animeName: String): List<Anime>? {
        val animeSearchResponse = retrofitAnimeInterface.searchAnime(animeName)

        return animeSearchResponse.body()
    }

    suspend fun getAnimeByCategory(categoryName: String): List<Anime>? {
        val categoryAnimeResponse = retrofitAnimeInterface.getAnimeByCategory(categoryName)

        return categoryAnimeResponse.body()
    }

    suspend fun getFromMalScraper(animeName: String): Anime? {
        val malScraperResponse = retrofitAnimeInterface.getFromMalScraper(animeName)

        return malScraperResponse.body()
    }

    suspend fun addToWatchList(requestAnime: Anime): Anime? {
        val watchlistResponse = retrofitAnimeInterface.addToWatchList(requestAnime)

        return watchlistResponse.body()
    }
}