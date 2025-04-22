package com.example.opboom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CabisoBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cabiso, container, false)

        // Initialize buttons
        val youtubeButton = view.findViewById<ImageButton>(R.id.youtubeButton)
        val linkedinButton = view.findViewById<ImageButton>(R.id.linkedinButton)

        // Set click listeners
        youtubeButton.setOnClickListener {
            openUrl("https://www.youtube.com/watch?v=4Jui6Prje6o") // Replace with actual YouTube URL
        }

        linkedinButton.setOnClickListener {
            openUrl("https://www.linkedin.com/in/chestine-may-mari-cabiso-4802161b4/?originalSubdomain=ph") // Replace with actual LinkedIn URL
        }

        return view
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}