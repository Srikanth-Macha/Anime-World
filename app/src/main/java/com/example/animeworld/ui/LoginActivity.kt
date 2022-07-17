package com.example.animeworld.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animeworld.databinding.ActivityLoginBinding

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
            val email = binding.emailInputEditText.text.toString()
            val password = binding.passwordInputEditText.text.toString()

            if (email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password.length >= 8) {
                    val username = email.substring(0, email.length - 10)

                    val preferences = getPreferences(Context.MODE_PRIVATE)
                    val editor = preferences.edit()

                    if(!preferences.contains("Email")) {
                        editor.apply {
                            putString("Username", username)
                            putString("Email", email)
                            putString("Password", password)
                            apply()
                        }
                    }
                    Toast.makeText(this, "Successfully logged in as $username", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }

        }

    }
}