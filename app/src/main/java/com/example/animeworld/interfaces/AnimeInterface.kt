package com.example.animeworld.interfaces

import android.util.Log
import com.example.animeworld.models.Anime
import com.example.animeworld.models.User
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

    suspend fun getAnimeByCategory(categoryName: String, pageNumber: Number): List<Anime>? {
        val categoryAnimeResponse =
            retrofitAnimeInterface.getAnimeByCategory(categoryName, pageNumber)

        return categoryAnimeResponse.body()
    }

    suspend fun getFromMalScraper(animeName: String): Anime? {
        val malScraperResponse = retrofitAnimeInterface.getFromMalScraper(animeName)

        return malScraperResponse.body()
    }

    suspend fun addToWatchList(requestAnime: Anime): Anime? {
        val postAnimeResponse = retrofitAnimeInterface.addToWatchList(requestAnime)

        return postAnimeResponse.body()
    }

    suspend fun getWatchList(email: String): List<Anime>? {
        val watchListResponse = retrofitAnimeInterface.getWatchList(email)

        return watchListResponse.body()
    }

    suspend fun loginUser(user: User): User? {
        val userResponse = retrofitAnimeInterface.loginUser(user)

        return userResponse.body()
    }
}