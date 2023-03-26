package com.example.animeworld.retrofit

import com.example.animeworld.models.Anime
import com.example.animeworld.models.User
import retrofit2.Response
import retrofit2.http.*

interface RetrofitAnimeInterface {

    // GET requests . . .

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

    @GET("/getFavouritesData")
    suspend fun getFavourites(@Query("email") email: String): Response<List<Anime>>

    @GET("/findAnimeByTag")
    suspend fun findAnimeByTag(@Query("anime_tag") animeTag: String): Response<List<Anime>>

    @GET("/similarAnime")
    suspend fun similarAnime(@Query("anime_tags") animeTags: List<String>?): Response<List<Anime>>

    @GET("/random")
    suspend fun getRandomAnime(): Response<Anime>

    // POST requests . . .

    @POST("/addToWatchList")
    suspend fun addToWatchList(@Body requestAnime: Anime): Response<Anime>

    @POST("/addToFavourites")
    suspend fun addToFavourites(@Body requestAnime: Anime): Response<Anime>

    @POST("/addUser")
    suspend fun loginUser(@Body user: User): Response<User?>


    // DELETE requests . . .

    @DELETE("/removeFromWatchList")
    suspend fun removeFromWatchList(
        @Query("anime_name") animeName: String,
        @Query("email") email: String,
    )

    @DELETE("/removeFromFavourites")
    suspend fun removeFromFavourites(
        @Query("anime_name") animeName: String,
        @Query("email") email: String,
    )
}