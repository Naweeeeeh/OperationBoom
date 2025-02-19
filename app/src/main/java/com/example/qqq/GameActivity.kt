package com.example.qqq

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.example.qqq.databinding.GamelayoutBinding

class GameActivity : AppCompatActivity() {
    private lateinit var game: MinesweeperGame
    private lateinit var minesweeperGrid: GridLayout
    private lateinit var binding: GamelayoutBinding


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GamelayoutBinding.inflate(layoutInflater)
        val view = binding.root
        Log.d("GameActivity", "onCreate called")
        setContentView(view)
        replaceFragment(HomeFragment())
        val activity = GameActivity()

        binding.bottomNavView.setOnItemSelectedListener { id ->

            when(id.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
                R.id.developer -> replaceFragment(DeveloperFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.logout -> replaceFragment(LogoutFragment())
            }
            return@setOnItemSelectedListener true
        }
//        try {
//            minesweeperGrid = findViewById(R.id.minesweeperGrid)
//            game = MinesweeperGame(8, 8, 10) // Example: 8x8 grid with 10 mines
//            initializeGrid()
//        } catch (e: Exception) {
//            Log.e("GameActivity", "Game Act error ni", e)
//            Toast.makeText(this, "Game Activity Error Occured.", Toast.LENGTH_LONG).show()
//            finish()
//        }
//        minesweeperGrid = findViewById(R.id.minesweeperGrid)
//        game = MinesweeperGame(8, 8, 10) // Example: 8x8 grid with 10 mines
//
//        initializeGrid()
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.gameframelayout, fragment)
        fragmentTransaction.commit()
    }


//
//    private fun initializeGrid() {
//        minesweeperGrid.removeAllViews()
//        minesweeperGrid.rowCount = game.rowCount
//        minesweeperGrid.columnCount = game.columnCount
//
//        for (row in 0 until game.rowCount) {
//            for (col in 0 until game.columnCount) {
//                val cellButton = Button(this)
//                cellButton.background = resources.getDrawable(R.drawable.borderradius, null)
//                cellButton.setOnClickListener {
//                    game.handleCellClick(row, col)
//                    updateGrid()
//                }
//                val params = GridLayout.LayoutParams().apply {
//                    width = 0
//                    height = ViewGroup.LayoutParams.WRAP_CONTENT
//                    columnSpec = GridLayout.spec(col, 1f)
//                    rowSpec = GridLayout.spec(row, 1f)
//                }
//                cellButton.layoutParams = params
//                minesweeperGrid.addView(cellButton)
//            }
//        }
//    }
//
//    private fun updateGrid() {
//        // Update the grid based on the game state
//        for (row in 0 until game.rowCount) {
//            for (col in 0 until game.columnCount) {
//                val cell = game.grid[row][col]
//                val button = minesweeperGrid.getChildAt(row * game.columnCount + col) as Button
//                button.text = cell.displayText
//                // Update button styles based on cell state (e.g., revealed, flagged, mine)
//            }
//        }
//    }
}
