package com.example.opboom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationActivity : Activity() {
    private lateinit var userRepository: UserRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        // Initialize Room Database
        val database = AppDatabase.getDatabase(this)
        userRepository = UserRepository(database.userDao(), database.gameProgressDao())

        val registerButton = findViewById<Button>(R.id.register) // Match this ID
        val backButton = findViewById<Button>(R.id.backButton) // Match this ID
        val usernameEditText = findViewById<EditText>(R.id.username) // Match this ID
        val passwordEditText = findViewById<EditText>(R.id.password) // Match this ID
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmpassword) // Match this ID

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Perform registration in a coroutine
            CoroutineScope(Dispatchers.Main).launch {
                val existingUser = userRepository.getUserByUsername(username)
                if (existingUser != null) {
                    Toast.makeText(this@RegistrationActivity, "Username already exists", Toast.LENGTH_SHORT).show()
                } else {
                    val userId = userRepository.registerUser(username, password)
                    if (userId > 0) {
                        Toast.makeText(this@RegistrationActivity, "Registration successful", Toast.LENGTH_SHORT).show()

                        // Pass username and password to LoginActivity
                        val intent = Intent(this@RegistrationActivity, LoginActivity::class.java).apply {
                            putExtra("username", username)
                            putExtra("password", password)
                        }
                        startActivity(intent)
                        finish() // Close the RegistrationActivity
                    } else {
                        Toast.makeText(this@RegistrationActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        backButton.setOnClickListener {
            Toast.makeText(this, "Back to Login", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close the RegistrationActivity
        }
    }
}