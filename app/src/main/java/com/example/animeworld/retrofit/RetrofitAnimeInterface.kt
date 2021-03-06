package com.example.animeworld.retrofit

import com.example.animeworld.models.Anime
import com.example.animeworld.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitAnimeInterface {
    @GET("/getPageData")
    suspend fun getAnimeData(@Query("page_number") pageNumber: Number): Response<List<Anime>>

    @GET("/findAnime")
    suspend fun searchAnime(@Query("anime_name") animeName: String): Response<List<Anime>>

    @GET("/getAnimeByCategory")
    suspend fun getAnimeByCategory(
        @Query("category_name") categoryName: String,
        @Query("page_number") pageNumber: Number = 1,
    ): Response<List<Anime>>

    @GET("/getFromMalScraper")
    suspend fun getFromMalScraper(@Query("anime_name") animeName: String): Response<Anime>

    @GET("/getWatchListData")
    suspend fun getWatchList(@Query("email") email: String): Response<List<Anime>>


    @POST("/addToWatchList")
    suspend fun addToWatchList(@Body requestAnime: Anime): Response<Anime>

    @POST("/addUser")
    suspend fun loginUser(@Body user: User): Response<User?>
}