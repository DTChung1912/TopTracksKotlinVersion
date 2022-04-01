package com.example.toptrackskotlinversion.Model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class Token {
    @SerializedName("token")
    @Expose
    var token: String? = null
}