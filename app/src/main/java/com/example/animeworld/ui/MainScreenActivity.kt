@file:Suppress("DEPRECATION")

package com.example.animeworld.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeworld.R
import com.example.animeworld.adapters.AnimeAdapter
import com.example.animeworld.databinding.ActivityMainScreenBinding
import com.example.animeworld.models.Anime
import com.example.animeworld.viewmodels.AnimeViewModel
import com.google.android.material.navigation.NavigationView

// TODO Custom messages if the user is not logged in

class MainScreenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var viewModel: AnimeViewModel
    private var searchAnimeLiveData: LiveData<Anime> = MutableLiveData()

    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // To show Navigation Drawer when clicked
        setNavigationView()

        viewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val animeLiveData = viewModel.showAnimePageData(currentPage)

        animeLiveData.observe(this) { animeList ->
            Log.w("AnimeList Response", animeList.toString())
            setRecyclerAdapter(animeList as MutableList<Anime>)
        }

        searchAnimeLiveData.observe(this) { searchedAnime ->
            Log.w("Anime from observe", searchedAnime.toString())
            searchNewAnime(searchedAnime)
        }
    }

    private fun searchNewAnime(anime: Anime) {
        val layoutManager = GridLayoutManager(this@MainScreenActivity, 2)
        val adapter = object : AnimeAdapter(
            mutableListOf(anime),
            this@MainScreenActivity,
            layoutManager,
            this@MainScreenActivity
        ) {
            override fun loadNextPage(page: Int) {}
        }

        setupRecyclerView(adapter, layoutManager)
    }

    private fun setRecyclerAdapter(
        animeList: MutableList<Anime>?,
    ) {
        val layoutManager = GridLayoutManager(this@MainScreenActivity, 2)
        val adapter = object : AnimeAdapter(
            animeList,
            this@MainScreenActivity,
            layoutManager,
            this@MainScreenActivity
        ) {
            @SuppressLint("NotifyDataSetChanged")
            override fun loadNextPage(page: Int) {
                binding.lottieLoading.visibility = View.VISIBLE

                val newAnimeLiveData = viewModel.showAnimePageData(page)

                newAnimeLiveData.observe(lifecycleOwner) { newAnimeList ->
                    animeList?.addAll(newAnimeList)
                    notifyDataSetChanged()

                    binding.lottieLoading.visibility = View.GONE
                }

            }

        }

        // Initiate RecyclerView-
        setupRecyclerView(adapter, layoutManager)
    }

    private fun setupRecyclerView(adapter: AnimeAdapter, layoutManager: GridLayoutManager) {
        binding.animeRecyclerView.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        binding.lottieLoading.visibility = View.GONE
    }

    private fun setNavigationView() {
        actionBarToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close)

        binding.drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarToggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                if (text != null && text.isNotBlank()) {
                    val i = Intent(this@MainScreenActivity, SearchResultsActivity::class.java)
                    i.putExtra("query text", text)

                    startActivity(i)
                } else {
                    Toast.makeText(this@MainScreenActivity,
                        "Search field cannot be empty",
                        Toast.LENGTH_SHORT).show()
                }

                return true
            }

            override fun onQueryTextChange(text: String?): Boolean = false
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val itemName = item.title.toString().lowercase()

        val navigationExceptions =
            arrayOf("categories") // No click-action on these menu items

        when (itemName) {
            in navigationExceptions -> {
                item.isCheckable = false
                return false
            }
            "sign out" -> {
                signOut()
                return true
            }
            "watch list" -> {
                startActivity(Intent(this@MainScreenActivity, WatchListActivity::class.java))
            }
            "favourites" -> {
                startActivity(Intent(this@MainScreenActivity, FavouritesActivity::class.java))
            }
            else -> {
                val intent =
                    Intent(this@MainScreenActivity, CategorizedAnimeActivity::class.java).apply {
                        putExtra("categoryName", item.title.toString())
                    }

                startActivity(intent)
            }
        }

        return true
    }

    private fun signOut() {
        Toast.makeText(this, "Signing out . . .", Toast.LENGTH_LONG).show()

        val preferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        editor.clear()
        editor.apply()

        startActivity(Intent(this@MainScreenActivity, LoginActivity::class.java))
        finishAffinity()
    }
}