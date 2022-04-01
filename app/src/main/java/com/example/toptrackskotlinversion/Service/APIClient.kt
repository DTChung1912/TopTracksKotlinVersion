package com.example.toptracks.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    const val url = "https://ws.audioscrobbler.com/2.0/"
    var retrofit: Retrofit? = null
    fun getAPIClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}