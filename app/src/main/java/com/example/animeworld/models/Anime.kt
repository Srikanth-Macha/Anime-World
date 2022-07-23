package com.example.animeworld.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Anime(
    @SerializedName("animeSeason")
    val animeSeason: AnimeSeason? = null,

    @SerializedName("episodes")
    val episodes: Int? = null,

    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("picture")
    val picture: String? = null,

    @SerializedName("relations")
    val relations: List<Any>? = null,

    @SerializedName("sources")
    val sources: List<String>? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("synonyms")
    val synonyms: List<String>? = null,

    @SerializedName("tags")
    val tags: List<Any>? = null,

    @SerializedName("thumbnail")
    val thumbnail: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("score")
    var score: String? = null,

    @SerializedName("shortDescription")
    var description: String? = null,

    @SerializedName("rating")
    var rating: String? = null,

    @SerializedName("startDate")
    var startDate: String? = null,

    @SerializedName("endDate")
    var endDate: String? = null,

    @SerializedName("user")
    var user: User? = null,
) : Serializable