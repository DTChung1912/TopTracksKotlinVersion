package com.example.toptracks.Service

import com.example.toptrackskotlinversion.Model.TopTracksModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicAPI {
    @GET("?method=artist.gettoptracks&artist=cher&format=json")
    fun getTrack(
        @Query("api_key") apikey: String?,
        @Query("limit") limit: Int
    ): Call<TopTracksModel>
}