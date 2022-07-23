package com.example.animeworld.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("username")
    var username: String? = null,

    @SerializedName("email")
    var email: String? = null,

    @SerializedName("password")
    var password: String? = null,
) : Serializable
