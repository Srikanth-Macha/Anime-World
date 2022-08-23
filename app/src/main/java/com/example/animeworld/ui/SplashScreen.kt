package com.example.animeworld.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animeworld.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val userEmail = preferences.getString("Email", "default")

        if (userEmail != "default") {
            Toast.makeText(this, "Hello $userEmail", Toast.LENGTH_SHORT).show()
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
        }, 1200)
    }
}