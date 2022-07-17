package com.example.animeworld.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animeworld.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val preferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val username = preferences.getString("Username", "default")

        if (username != "default") {
            Toast.makeText(this, "Hello $username", Toast.LENGTH_SHORT).show()
            moveToNextScreen(Intent(this, MainScreenActivity::class.java))

        } else {
            moveToNextScreen(Intent(this, LoginActivity::class.java))
        }
    }

    private fun moveToNextScreen(intent: Intent) {
        actionBar?.hide()
        supportActionBar?.hide()

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 1000)
    }
}