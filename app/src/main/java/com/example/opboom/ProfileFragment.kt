package com.example.opboom

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var profileImageView: ImageView
    private lateinit var sharedPref: SharedPreferences
    private val avatarIcons = listOf(
        R.drawable.ledon,
        R.drawable.cat,
        R.drawable.hacker,
        R.drawable.sigmaboy
    )
    private val AVATAR_PREF_KEY = "selected_avatar"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sharedPref = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        profileImageView = view.findViewById(R.id.profileID)
        val usernameTextView = view.findViewById<TextView>(R.id.username)
        val rankTextView = view.findViewById<TextView>(R.id.rank)
        val editAvatarButton = view.findViewById<Button>(R.id.editAvatar)

        // Load saved username or default
        val username = sharedPref.getString("username", "Player") ?: "Player"
        usernameTextView.text = "Welcome $username!"

        // Set rank based on username length (customize this logic)
        rankTextView.text = "Rank: ${getUserRank(username)}"

        // Load saved avatar or default
        val savedAvatar = sharedPref.getInt(AVATAR_PREF_KEY, R.drawable.ledon)
        profileImageView.setImageResource(savedAvatar)

        editAvatarButton.setOnClickListener {
            showAvatarSelectionDialog()
        }
        return view
    }

    private fun getUserRank(username: String): String {
        return when {
            username.length < 5 -> "Newbie"
            username.length < 10 -> "Intermediate"
            else -> "Minesweeper Master"
        }
    }

    private fun showAvatarSelectionDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_avatar_grid, null)
        val gridView = dialogView.findViewById<GridView>(R.id.avatarGrid)

        val adapter = AvatarAdapter(requireContext(), avatarIcons) { selectedIcon ->
            with(sharedPref.edit()) {
                putInt(AVATAR_PREF_KEY, selectedIcon)
                apply()
            }
            profileImageView.setImageResource(selectedIcon)
        }

        gridView.adapter = adapter

        AlertDialog.Builder(requireContext())
            .setTitle("Select Avatar")
            .setView(dialogView)
            .setNegativeButton("Done") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}