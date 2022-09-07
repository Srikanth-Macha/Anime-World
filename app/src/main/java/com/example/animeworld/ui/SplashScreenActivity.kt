package com.example.animeworld.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animeworld.R
import com.example.animeworld.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    companion object {
        private const val ANIMATION_DURATION = 1000L
    }

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAnimation()

        val preferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val userEmail = preferences.getString("Email", "default")
        val username = preferences.getString("Username", null)

        if (userEmail != "default") {
            Toast.makeText(this, "Hello $username", Toast.LENGTH_SHORT).show()
            moveToNextScreen(Intent(this, MainScreenActivity::class.java))

        } else {
            moveToNextScreen(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setAnimation() {
        binding.splashScreenLogo.animation =
            AnimationUtils.loadAnimation(this, R.anim.fade_animation).apply {
                duration = ANIMATION_DURATION
            }

        binding.animeWorld.apply {
            animate().translationY(-280f).duration = ANIMATION_DURATION

            animation =
                AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.fade_animation)
                    .apply {
                        duration = ANIMATION_DURATION
                    }
        }
    }

    @Suppress("DEPRECATION") // For Handler()
    private fun moveToNextScreen(intent: Intent) {
        actionBar?.hide()
        supportActionBar?.hide()

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 1200)
    }
}
