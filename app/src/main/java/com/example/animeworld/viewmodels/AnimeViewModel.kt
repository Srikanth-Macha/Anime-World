package com.example.animeworld.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeworld.interfaces.AnimeInterface
import com.example.animeworld.models.Anime
import com.example.animeworld.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimeViewModel : ViewModel() {
    fun showAnimePageData(pageNumber: Int): LiveData<List<Anime>> {
        val animeLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch {
            val list = AnimeInterface.getAnimeData(pageNumber)

            withContext(Dispatchers.Main) {
                animeLiveData.value = list
            }

        }

        return animeLiveData
    }

    fun searchAnime(animeName: String): LiveData<List<Anime>> {
        val animeSearchLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch {
            val searchedAnime = AnimeInterface.searchAnimeByName(animeName)

            withContext(Dispatchers.Main) {
                animeSearchLiveData.value = searchedAnime
            }
        }

        return animeSearchLiveData
    }

    fun getCategorizedAnime(categoryName: String, pageNumber: Number): LiveData<List<Anime>> {
        val categorizedAnimeLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch(Dispatchers.IO) {
            val categorizedAnimeList =
                AnimeInterface.getAnimeByCategory(categoryName.lowercase().trim(), pageNumber)

            withContext(Dispatchers.Main) {
                categorizedAnimeLiveData.value = categorizedAnimeList
            }
        }

        return categorizedAnimeLiveData
    }

    fun getDataFromMalScraper(animeName: String): LiveData<Anime> {
        val malScraperLiveData = MutableLiveData<Anime>()

        viewModelScope.launch(Dispatchers.IO) {
            val malScraperData = AnimeInterface.getFromMalScraper(animeName)

            withContext(Dispatchers.Main) {
                malScraperLiveData.value = malScraperData
            }
        }

        return malScraperLiveData
    }

    fun getWatchList(email: String?): LiveData<List<Anime>> {
        val watchListLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch(Dispatchers.IO) {
            val watchList = AnimeInterface.getWatchList(email.toString())

            withContext(Dispatchers.Main) {
                watchListLiveData.value = watchList
            }
        }

        return watchListLiveData
    }

    fun getFavourites(email: String): LiveData<List<Anime>> {
        val favouritesLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch(Dispatchers.IO) {
            val favourites = AnimeInterface.getFavourites(email)

            withContext(Dispatchers.Main) {
                favouritesLiveData.value = favourites
            }
        }

        return favouritesLiveData
    }

    fun findAnimeByTag(animeTag: String): LiveData<List<Anime>> {
        val animeTagLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch(Dispatchers.IO) {
            val animeTagSearch = AnimeInterface.findAnimeByTag(animeTag)

            withContext(Dispatchers.Main) {
                animeTagLiveData.value = animeTagSearch
            }
        }

        return animeTagLiveData
    }

    fun similarAnime(animeTags: List<String>?): LiveData<List<Anime>> {
        val similarAnimeLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch(Dispatchers.IO) {
            val similarAnime = AnimeInterface.similarAnime(animeTags)

            withContext(Dispatchers.Main) {
                similarAnimeLiveData.value = similarAnime
            }
        }

        return similarAnimeLiveData
    }

    fun addAnimeToWatchList(requestAnime: Anime): LiveData<Anime> {
        val postAnimeLiveData = MutableLiveData<Anime>()

        viewModelScope.launch(Dispatchers.IO) {
            val watchListData = AnimeInterface.addToWatchList(requestAnime)

            withContext(Dispatchers.Main) {
                postAnimeLiveData.value = watchListData
            }
        }

        return postAnimeLiveData
    }

    fun addAnimeToFavourites(requestAnime: Anime): LiveData<Anime> {
        val postAnimeLiveData = MutableLiveData<Anime>()

        viewModelScope.launch(Dispatchers.IO) {
            val watchListData = AnimeInterface.addToFavourites(requestAnime)

            withContext(Dispatchers.Main) {
                postAnimeLiveData.value = watchListData
            }
        }

        return postAnimeLiveData
    }

    fun loginUser(user: User): LiveData<User> {
        val userLiveData = MutableLiveData<User>()

        viewModelScope.launch(Dispatchers.IO) {
            val userResponse = AnimeInterface.loginUser(user)

            withContext(Dispatchers.Main) {
                userLiveData.value = userResponse
            }
        }

        return userLiveData
    }

    fun removeFromWatchList(animeName: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            AnimeInterface.removeFromWatchList(animeName, email)
        }
    }

    fun removeFromFavourites(animeName: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            AnimeInterface.removeFromFavourites(animeName, email)
        }
    }

    fun getRandomAnime(): LiveData<Anime> {
        val liveData = MutableLiveData<Anime>()

        viewModelScope.launch(Dispatchers.IO) {
            val data = AnimeInterface.getRandomAnime()

            withContext(Dispatchers.Main) {
                liveData.value = data
            }
        }

        return liveData
    }
}