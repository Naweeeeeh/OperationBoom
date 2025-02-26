package com.example.opboom

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.app.AlertDialog
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    private lateinit var widthInput: EditText
    private lateinit var heightInput: EditText
    private lateinit var minesInput: EditText
    private lateinit var saveButton: Button
    private lateinit var mineColorPickerButton: Button
    private lateinit var numberColorPickerButton: Button
    private var selectedMineColor: Int = Color.RED
    private var selectedNumberColor: Int = Color.BLUE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        widthInput = view.findViewById(R.id.widthInput)
        heightInput = view.findViewById(R.id.heightInput)
        minesInput = view.findViewById(R.id.minesInput)
        saveButton = view.findViewById(R.id.saveButton)
        mineColorPickerButton = view.findViewById(R.id.mineColorPickerButton)
        numberColorPickerButton = view.findViewById(R.id.numberColorPickerButton)

        saveButton.setOnClickListener {
            saveSettings()
        }

        mineColorPickerButton.setOnClickListener {
            showColorPickerDialog("mine")
        }

        numberColorPickerButton.setOnClickListener {
            showColorPickerDialog("number")
        }

        return view
    }

    private fun saveSettings() {
        val width = widthInput.text.toString().toIntOrNull() ?: 8
        val height = heightInput.text.toString().toIntOrNull() ?: 8
        val mines = minesInput.text.toString().toIntOrNull() ?: 10

        val sharedPref = requireActivity().getSharedPreferences("MinesweeperSettings", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("width", width)
            putInt("height", height)
            putInt("mines", mines)
            putInt("mineColor", selectedMineColor) // Save mine color
            putInt("numberColor", selectedNumberColor) // Save number color
            apply()
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Settings Saved")
            .setMessage("Your settings have been saved successfully.")
            .setPositiveButton("OK", null)
            .show()

        val activity = requireActivity() as GameActivity
        activity.binding.bottomNavView.selectedItemId = R.id.home
        activity.replaceFragment(HomeFragment())
    }

    private fun showColorPickerDialog(type: String) {
        val colors = arrayOf(
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.BLACK, Color.WHITE
        )
        val colorNames = arrayOf("Red", "Green", "Blue", "Yellow", "Cyan", "Magenta", "Black", "White")

        AlertDialog.Builder(requireContext())
            .setTitle("Choose ${type.replaceFirstChar { it.uppercase() }} Color")
            .setItems(colorNames) { _, which ->
                when (type) {
                    "mine" -> {
                        selectedMineColor = colors[which]
                        mineColorPickerButton.setBackgroundColor(selectedMineColor)
                    }
                    "number" -> {
                        selectedNumberColor = colors[which]
                        numberColorPickerButton.setBackgroundColor(selectedNumberColor)
                    }
                }
            }
            .show()
    }
}