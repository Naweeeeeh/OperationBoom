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

class LoginActivity : Activity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val loginButton = findViewById<Button>(R.id.Login)
        val signUpButton = findViewById<Button>(R.id.signUp)
        val usernameEditText = findViewById<EditText>(R.id.loginusername)
        val passwordEditText = findViewById<EditText>(R.id.loginpassword)

        val registerUsername = findViewById<EditText>(R.id.registerusername)
        val registerPassword = findViewById<EditText>(R.id.registerpassword)


        intent?.let {
            intent.getStringExtra("username")?.let { user ->
                usernameEditText.setText(user)
            }
        }
        intent?.let {
            intent.getStringExtra("password")?.let { pass ->
                passwordEditText.setText(pass)
            }
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val enteredUsername = usernameEditText
            val enteredPassword = passwordEditText

            if (enteredUsername.text.toString().isEmpty() || enteredPassword.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }


//
//            CoroutineScope(Dispatchers.Main).launch {
//                val user = userRepository.loginUser(enteredUsername.text.toString(), enteredPassword.text.toString())
//                if (user != null) {
//                    Toast.makeText(this@LoginActivity, "Welcome, ${user.username}!", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this@LoginActivity, GameActivity::class.java)
//                    intent.putExtra("userId", user.id)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
//                }
//            } //trying so hard wwwwwwwwhyyyyyyyyyyyy
//         } //pls do ignore these, trial and error for the login activity. Tried communicating from class to class but failed
            // so yea I guess I'll find some help soon
        }
//        loginButton.setOnClickListener {
//            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, GameActivity::class.java)
//            startActivity(intent)
//        }

}
