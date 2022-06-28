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
) : Serializable

/*
{
    "_id": "628a4ff33bc4b4b68bcd0109",
    "sources": [
      "https://anidb.net/anime/16157"
    ],
    "title": "Zutto Mayonaka de Iinoni.: Tei Chi Boruto",
    "type": "SPECIAL",
    "episodes": 1,
    "status": "FINISHED",
    "animeSeason": {
      "season": "SUMMER",
      "year": 2020
    },
    "picture": "https://cdn.anidb.net/images/main/258631.jpg",
    "thumbnail": "https://cdn.anidb.net/images/main/258631.jpg-thumb.jpg",
    "synonyms": [
      "Zutto Mayonaka de Iinoni.: Fastening",
      "ずっと真夜中でいいのに。『低血ボルト』"
    ],
    "relations": [

    ],
    "tags": [

    ]
 }
  */