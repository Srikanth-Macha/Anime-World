package com.example.animeworld.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AnimeSeason(
    @SerializedName("season")
    val season: String? = null,

    @SerializedName("year")
    val year: Int? = null,
) : Serializable {
    override fun toString(): String = "$season $year"
}