package com.example.toptrackskotlinversion.View

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.toptrackskotlinversion.Model.Constants
import com.example.toptrackskotlinversion.R

class StartActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val sharedPreferences: SharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE)
        var userName = sharedPreferences.getString(Constants.KEY_CURRENT_USER, null)
        if (userName != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }
}