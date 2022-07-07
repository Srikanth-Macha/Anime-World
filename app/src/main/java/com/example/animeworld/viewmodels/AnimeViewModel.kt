package com.example.animeworld.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeworld.interfaces.AnimeInterface
import com.example.animeworld.models.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimeViewModel : ViewModel() {
    private val animeInterface = AnimeInterface

    fun showAnimePageData(pageNumber: Int): LiveData<List<Anime>> {
        val animeLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch {
            val list = animeInterface.getAnimeData(pageNumber)

            withContext(Dispatchers.Main) {
                animeLiveData.value = list
            }

        }

        return animeLiveData
    }

    fun searchAnime(animeName: String): LiveData<List<Anime>> {
        val animeSearchLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch {
            val searchedAnime = animeInterface.searchAnimeByName(animeName)

            withContext(Dispatchers.Main) {
                animeSearchLiveData.value = searchedAnime
                Log.w("Search Anime Response", searchedAnime.toString())
            }
        }

        return animeSearchLiveData
    }

    fun getCategorizedAnime(categoryName: String): LiveData<List<Anime>> {
        val categorizedAnimeLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch(Dispatchers.IO) {
            val categorizedAnimeList = animeInterface.getAnimeByCategory(categoryName.lowercase())

            withContext(Dispatchers.Main) {
                categorizedAnimeLiveData.value = categorizedAnimeList
            }
        }

        return categorizedAnimeLiveData
    }

    fun getDataFromMalScraper(animeName: String): LiveData<Anime> {
        val malScraperLiveData = MutableLiveData<Anime>()

        viewModelScope.launch(Dispatchers.IO) {
            val malScraperData = animeInterface.getFromMalScraper(animeName)

            withContext(Dispatchers.Main) {
                malScraperLiveData.value = malScraperData
            }
        }

        return malScraperLiveData
    }

    fun addAnimeToWatchList(requestAnime: Anime): LiveData<Anime> {
        val postAnimeLiveData = MutableLiveData<Anime>()

        viewModelScope.launch(Dispatchers.IO) {
            val watchListData = animeInterface.addToWatchList(requestAnime)

            withContext(Dispatchers.Main) {
                postAnimeLiveData.value = watchListData
            }
        }

        return postAnimeLiveData
    }

    fun getWatchList(): LiveData<List<Anime>> {
        val watchListLiveData = MutableLiveData<List<Anime>>()

        viewModelScope.launch(Dispatchers.IO) {
            val watchList = animeInterface.getWatchList()

            withContext(Dispatchers.Main) {
                watchListLiveData.value = watchList
            }
        }

        return watchListLiveData
    }

}