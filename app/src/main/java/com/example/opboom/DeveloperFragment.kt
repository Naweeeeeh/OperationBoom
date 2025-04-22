package com.example.opboom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeveloperFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_developer, container, false)

        val boiserButton = view.findViewById<Button>(R.id.boiser)
        val cabisoButton = view.findViewById<Button>(R.id.cabiso)

        boiserButton.setOnClickListener {
            val boiserBottomSheet = BoiserBottomSheetFragment()
            boiserBottomSheet.show(parentFragmentManager, boiserBottomSheet.tag)
        }

        cabisoButton.setOnClickListener {
            val cabisoBottomSheet = CabisoBottomSheetFragment()
            cabisoBottomSheet.show(parentFragmentManager, cabisoBottomSheet.tag)
        }

        return view
    }
}