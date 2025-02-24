package com.example.opboom

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.opboom.databinding.GamelayoutBinding

class GameActivity : AppCompatActivity() {
    lateinit var binding: GamelayoutBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GamelayoutBinding.inflate(layoutInflater)
        val view = binding.root
        Log.d("GameActivity", "onCreate called")
        setContentView(view)
        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener { id ->
            when (id.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
                R.id.developer -> replaceFragment(DeveloperFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.logout -> replaceFragment(LogoutFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.gameframelayout, fragment)
        fragmentTransaction.commit()
    }

}
