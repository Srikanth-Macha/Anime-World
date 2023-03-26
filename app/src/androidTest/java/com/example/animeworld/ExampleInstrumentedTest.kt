package com.example.animeworld

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.animeworld.models.Anime
import com.example.animeworld.models.User
import com.example.animeworld.retrofit.RetrofitAnimeInterface
import com.example.animeworld.viewmodels.AnimeViewModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

val database = listOf(
    Anime(title = "AOT", rating = "4.8"),
    Anime(title = "Black clover", rating = "4.6"),
    Anime(title = "Demon Slayer", rating = "4.6"),
    Anime(title = "Dragon Ball", rating = "4.7"),
    Anime(title = "One Punch Man", rating = "4.3"),
    Anime(title = "Naruto", rating = "4.6"),
    Anime(title = "Death Note", rating = "4.5"),
    Anime(title = "Overlord", rating = "4.4")
)


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val viewModel = AnimeViewModel()
        val sampleInterface = SampleInterface()

        viewModel.testData(sampleInterface).observe(FakeLifeCycleOwner()) {
            assertEquals(it, database)
        }
    }
}


class SampleInterface : RetrofitAnimeInterface {
    override suspend fun getAnimeData(pageNumber: Number): Response<List<Anime>> {
        return Response.success(database)
    }

    override suspend fun searchAnime(animeName: String): Response<List<Anime>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAnimeByCategory(
        categoryName: String,
        pageNumber: Number
    ): Response<List<Anime>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFromMalScraper(animeName: String): Response<Anime> {
        TODO("Not yet implemented")
    }

    override suspend fun getWatchList(email: String): Response<List<Anime>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavourites(email: String): Response<List<Anime>> {
        TODO("Not yet implemented")
    }

    override suspend fun findAnimeByTag(animeTag: String): Response<List<Anime>> {
        TODO("Not yet implemented")
    }

    override suspend fun similarAnime(animeTags: List<String>?): Response<List<Anime>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToWatchList(requestAnime: Anime): Response<Anime> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavourites(requestAnime: Anime): Response<Anime> {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(user: User): Response<User?> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromWatchList(animeName: String, email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavourites(animeName: String, email: String) {
        TODO("Not yet implemented")
    }

}


class FakeLifeCycleOwner : LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        return LifecycleRegistry(this)
    }
}