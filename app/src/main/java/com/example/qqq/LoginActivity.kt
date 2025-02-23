package com.example.qqq

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : Activity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val loginButton = findViewById<Button>(R.id.Login)
        val signUpButton = findViewById<Button>(R.id.signUp)
        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)

        signUpButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

//        loginButton.setOnClickListener {
////            val username = usernameEditText.text.toString().trim()
////            val password = passwordEditText.text.toString()
////
////            if (username.isEmpty() || password.isEmpty()) {
////                Toast.makeText(this, "Please enter both username and password.", Toast.LENGTH_SHORT).show()
////            }
////
////            val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
////            val storedUsername = sharedPreferences.getString("username", null)
////            val storedPassword = sharedPreferences.getString("password", null)
////
////            Log.d("LoginActivity", "Entered Username: $username")
////            Log.d("LoginActivity", "Stored Username: $storedUsername")
////
////            if (storedUsername == null || storedPassword == null) {
////                Toast.makeText(this, "No user found. Please register first.", Toast.LENGTH_SHORT).show()
////            } else if (username == storedUsername && password == storedPassword) {
////                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
////                val intent = Intent(this, GameActivity::class.java)
////                startActivity(intent)
////            } else {
////                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
////            }
//        }
        loginButton.setOnClickListener {
            Toast.makeText(this, "BOOM", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}
