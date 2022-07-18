package com.example.animeworld.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    var username: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("password")
    var password: String? = null,
)
