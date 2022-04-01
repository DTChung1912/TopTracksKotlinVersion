package com.example.toptrackskotlinversion.Model

import com.google.gson.annotations.SerializedName

data class Track(
    val artist: Artist,
    @SerializedName("@attr")
    val attr: AttrX,
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val streamable: String,
    val url: String
)