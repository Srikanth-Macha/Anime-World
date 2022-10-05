package com.example.animeworld.interfaces

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

    suspend fun getWatchList(email: String): List<Anime>? {
        val watchListResponse = retrofitAnimeInterface.getWatchList(email)

        return watchListResponse.body()
    }

    suspend fun getFavourites(email: String): List<Anime>? {
        val favouritesResponse = retrofitAnimeInterface.getFavourites(email)

        return favouritesResponse.body()
    }

    suspend fun findAnimeByTag(animeTag: String): List<Anime>? {
        val animeTagResponse = retrofitAnimeInterface.findAnimeByTag(animeTag)

        return animeTagResponse.body()
    }

    suspend fun addToWatchList(requestAnime: Anime): Anime? {
        val postAnimeResponse = retrofitAnimeInterface.addToWatchList(requestAnime)

        return postAnimeResponse.body()
    }

    suspend fun addToFavourites(requestAnime: Anime): Anime? {
        val postAnimeResponse = retrofitAnimeInterface.addToFavourites(requestAnime)

        return postAnimeResponse.body()
    }

    suspend fun loginUser(user: User): User? {
        val userResponse = retrofitAnimeInterface.loginUser(user)

        return userResponse.body()
    }

    suspend fun removeFromWatchList(animeName: String, email: String) {
        retrofitAnimeInterface.removeFromWatchList(animeName, email)
    }

    suspend fun removeFromFavourites(animeName: String, email: String) {
        retrofitAnimeInterface.removeFromFavourites(animeName, email)
    }
}