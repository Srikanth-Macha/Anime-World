package com.example.animeworld.retrofit

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MyRetrofit {
    private const val API_URL = /*"http://:3000/"*/
        "https://anime-backend-naj1.onrender.com"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().apply {
                connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
                    .protocols(listOf(Protocol.HTTP_1_1))
                connectTimeout(60, TimeUnit.SECONDS)
                writeTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
                retryOnConnectionFailure(false)
            }.build())
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}