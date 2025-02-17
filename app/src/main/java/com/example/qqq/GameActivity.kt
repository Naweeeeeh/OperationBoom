package com.example.qqq

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.GridLayout
import android.widget.Toast

class GameActivity : Activity() {
    private lateinit var game: MinesweeperGame
    private lateinit var minesweeperGrid: GridLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("GameActivity", "onCreate called")
        setContentView(R.layout.gamelayout)
        try {
            minesweeperGrid = findViewById(R.id.minesweeperGrid)
            game = MinesweeperGame(8, 8, 10) // Example: 8x8 grid with 10 mines
            initializeGrid()
        } catch (e: Exception) {
            Log.e("GameActivity", "Game Act error ni", e)
            Toast.makeText(this, "Game Activity Error Occured.", Toast.LENGTH_LONG).show()
            finish()
        }
        minesweeperGrid = findViewById(R.id.minesweeperGrid)
        game = MinesweeperGame(8, 8, 10) // Example: 8x8 grid with 10 mines

        initializeGrid()
    }

    private fun initializeGrid() {
        minesweeperGrid.removeAllViews()
        minesweeperGrid.rowCount = game.rowCount
        minesweeperGrid.columnCount = game.columnCount

        for (row in 0 until game.rowCount) {
            for (col in 0 until game.columnCount) {
                val cellButton = Button(this)
                cellButton.background = resources.getDrawable(R.drawable.borderradius, null)
                cellButton.setOnClickListener {
                    game.handleCellClick(row, col)
                    updateGrid()
                }
                val params = GridLayout.LayoutParams().apply {
                    width = 0
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(col, 1f)
                    rowSpec = GridLayout.spec(row, 1f)
                }
                cellButton.layoutParams = params
                minesweeperGrid.addView(cellButton)
            }
        }
    }

    private fun updateGrid() {
        // Update the grid based on the game state
        for (row in 0 until game.rowCount) {
            for (col in 0 until game.columnCount) {
                val cell = game.grid[row][col]
                val button = minesweeperGrid.getChildAt(row * game.columnCount + col) as Button
                button.text = cell.displayText
                // Update button styles based on cell state (e.g., revealed, flagged, mine)
            }
        }
    }
}