package com.example.animeworld.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.animeworld.databinding.ActivityLoginBinding
import com.example.animeworld.models.User
import com.example.animeworld.viewmodels.AnimeViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.skipButton.setOnClickListener {
            startActivity(Intent(this, MainScreenActivity::class.java))
            finishAffinity()
        }

        binding.loginButton.setOnClickListener {
            binding.progressBar.isVisible = true

            val email = binding.emailInputEditText.text.toString()
            val password = binding.passwordInputEditText.text.toString()

            if (email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password.length >= 8) {
                    val username = email.substring(0, email.length - 10)

                    loginUser(User(username, email, password))
                } else {
                    Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun loginUser(user: User) {
        val viewModel = ViewModelProvider(this)[AnimeViewModel::class.java]
        val userLiveData = viewModel.loginUser(user)

        userLiveData.observe(this) { searchedUser ->
            if (searchedUser?.username == null && searchedUser?.email == null) {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT)
                    .show()

                return@observe
            } else {
                moveToMainScreen(user) // to Login and show main screen
            }
        }
    }

    private fun moveToMainScreen(user: User) {
        val preferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        if (!preferences.contains("Email")) {
            editor.apply {
                putString("Username", user.username)
                putString("Email", user.email)
                putString("Password", user.password)
                apply()
            }
        }
        Toast.makeText(this, "Successfully logged in as ${user.username}", Toast.LENGTH_SHORT)
            .show()

        binding.progressBar.visibility = View.GONE

        startActivity(Intent(this, MainScreenActivity::class.java))
        finishAffinity()
    }
}