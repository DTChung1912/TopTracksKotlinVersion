package com.example.toptracks.Service

import com.example.toptrackskotlinversion.Model.Token
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TokenAPI {
    @GET("?method=auth.gettoken&format=json")
    fun getToken(
        @Query("api_key") apikey: String?
    ): Call<Token?>?
}