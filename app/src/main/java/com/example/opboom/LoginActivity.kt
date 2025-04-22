package com.example.opboom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
        val usernameEditText = findViewById<EditText>(R.id.loginusername)
        val passwordEditText = findViewById<EditText>(R.id.loginpassword)

        intent?.let {
            intent.getStringExtra("username")?.let { user ->
                usernameEditText.setText(user)
            }
            intent.getStringExtra("password")?.let { pass ->
                passwordEditText.setText(pass)
            }
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val enteredUsername = usernameEditText.text.toString()
            val enteredPassword = passwordEditText.text.toString()

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("username", enteredUsername)
                apply()
            }

            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}