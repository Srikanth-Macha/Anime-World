package com.example.animeworld.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeworld.R
import com.example.animeworld.models.Anime
import com.example.animeworld.ui.AnimeInfoActivity

abstract class AnimeAdapter(
    private val animeList: List<Anime>?,
    private val context: Context,
    private val layoutManager: GridLayoutManager,
    val lifecycleOwner: LifecycleOwner,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val LOADING = 1
        const val ITEM = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewItem = LayoutInflater.from(context)
            .inflate(R.layout.anime_item, parent, false)

        return AnimeViewHolder(viewItem)

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as AnimeViewHolder

        if (animeList != null) {
            holder.animeName.text = animeList[position].title
            holder.onItemClick(context, animeList)

            Glide.with(context).load(animeList[position].picture)
                .into(holder.animeImage)
        }

        Log.i("Item Number: ", position.toString())

        val firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        val visibleItems = layoutManager.childCount

        if (firstVisibleItemPosition + visibleItems >= itemCount - 1) {
            loadNextPage(itemCount / 30 + 1)
        }

    }

    abstract fun loadNextPage(page: Int)

    override fun getItemCount(): Int = animeList?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return if (position == animeList?.size) LOADING
        else ITEM
    }

}

class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val animeName: TextView = itemView.findViewById(R.id.animeName)
    val animeImage: ImageView = itemView.findViewById(R.id.animeImage)

    fun onItemClick(context: Context, animeList: List<Anime>) {
        animeImage.setOnClickListener {
            Log.i("item name", animeName.text.toString())

            val i = Intent(context, AnimeInfoActivity::class.java)
            i.putExtra("anime info", animeList[adapterPosition])
            context.startActivity(i)
        }
    }

}