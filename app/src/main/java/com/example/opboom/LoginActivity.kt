package com.example.opboom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : Activity() {
    private lateinit var userRepository: UserRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val loginButton = findViewById<Button>(R.id.Login) // Match this ID
        val signUpButton = findViewById<Button>(R.id.signUp) // Match this ID
        val usernameEditText = findViewById<EditText>(R.id.username) // Match this ID
        val passwordEditText = findViewById<EditText>(R.id.password) // Match this ID

        val database = AppDatabase.getDatabase(this)
        userRepository = UserRepository(database.userDao(), database.gameProgressDao())

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        if (!username.isNullOrEmpty()) {
            usernameEditText.setText(username)
        }
        if (!password.isNullOrEmpty()) {
            passwordEditText.setText(password)
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

//        loginButton.setOnClickListener {
//            val enteredUsername = usernameEditText.text.toString()
//            val enteredPassword = passwordEditText.text.toString()
//
//            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
//                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Perform login in a coroutine
//            CoroutineScope(Dispatchers.Main).launch {
//                val user = userRepository.loginUser(enteredUsername, enteredPassword)
//                if (user != null) {
//                    Toast.makeText(this@LoginActivity, "Welcome, ${user.username}!", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this@LoginActivity, GameActivity::class.java)
//                    intent.putExtra("userId", user.id) // Pass the user ID to GameActivity
//                    startActivity(intent)
//                    finish() // Close the LoginActivity
//                } else {
//                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
//                }
//            } trying so hard wwwwwwwwhyyyyyyyyyyyy
//        } pls do ignore these, trial and error for the login activity. Tried communicating from class to class but failed
        // so yea I guess I'll find some help soon
        loginButton.setOnClickListener {
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}
