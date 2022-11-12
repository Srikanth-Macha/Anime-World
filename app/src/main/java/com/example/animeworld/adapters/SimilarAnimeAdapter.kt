package com.example.animeworld.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeworld.R
import com.example.animeworld.models.Anime
import com.example.animeworld.ui.AnimeInfoActivity

class SimilarAnimeAdapter(
    private val context: Context,
    private val similarAnimeList: List<Anime>
) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.similar_anime_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(similarAnimeList[position].picture).into(holder.animeImage)
        holder.onItemClick(context, similarAnimeList[position])
    }

    override fun getItemCount(): Int = similarAnimeList.size
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val animeImage: ImageView = itemView.findViewById(R.id.similarAnimeImage)

    fun onItemClick(context: Context, anime: Anime) {
        animeImage.setOnClickListener {
            val i = Intent(context, AnimeInfoActivity::class.java).apply {
                putExtra("anime info", anime)
                putExtra("activity name", context.javaClass.name)
            }
            context.startActivity(i)
        }
    }
}