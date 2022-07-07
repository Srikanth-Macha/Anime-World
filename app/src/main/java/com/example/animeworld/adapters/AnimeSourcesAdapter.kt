package com.example.animeworld.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animeworld.R

class AnimeSourcesAdapter(
    private val context: Context,
    private val sources: List<String>,
) : RecyclerView.Adapter<SourcesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.anime_sources_item, parent, false)

        return SourcesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SourcesViewHolder, position: Int) {
        holder.animeSourceItem.text = sources[position]
        holder.onItemClick(context)
    }

    override fun getItemCount(): Int = sources.size

}

class SourcesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val animeSourceItem: TextView = view.findViewById(R.id.animeSourceItemID)

    fun onItemClick(context: Context) {
        animeSourceItem.setOnClickListener { clickedItem ->
            val sourceItem = clickedItem as TextView

            val sourceItemText = sourceItem.text
            sourceItem.setBackgroundColor(Color.parseColor("#00428A"))

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(sourceItemText.toString())
            context.startActivity(intent)
        }
    }

}