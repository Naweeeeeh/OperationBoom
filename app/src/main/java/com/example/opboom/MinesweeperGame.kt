package com.example.opboom

import android.util.Log

data class Cell(
    var hasMine: Boolean = false,
    var isRevealed: Boolean = false,
    var isFlagged: Boolean = false,
    var adjacentMines: Int = 0
) {
    val displayText: String
        get() = when {
            isFlagged -> "F"
            !isRevealed -> ""
            hasMine -> "M"
            adjacentMines > 0 -> adjacentMines.toString()
            else -> ""
        }
}


class MinesweeperGame(private val rows: Int, private val cols: Int, private val mines: Int) {

    val grid: Array<Array<Cell>>

    init {
        Log.d("MinesweeperGame", "Initializing game with $rows rows, $cols columns, and $mines mines.")
        grid = Array(rows) { Array(cols) { Cell() } }

        try {
            placeMines()
            calculateAdjacentMines()
        } catch (e: Exception) {
            Log.e("MinesweeperGame", "Error during game setup", e)
        }
    }

    private fun placeMines() {
        var placedMines = 0
        while (placedMines < mines) {
            val row = (0 until rows).random()
            val col = (0 until cols).random()
            if (!grid[row][col].hasMine) {
                grid[row][col].hasMine = true
                placedMines++
            }
        }
    }

    private fun calculateAdjacentMines() {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (grid[row][col].hasMine) continue
                var count = 0
                for (dr in -1..1) {
                    for (dc in -1..1) {
                        val r = row + dr
                        val c = col + dc
                        if (r in 0 until rows && c in 0 until cols && grid[r][c].hasMine) {
                            count++
                        }
                    }
                }
                grid[row][col].adjacentMines = count
            }
        }
    }

    fun handleCellClick(row: Int, col: Int) {
        if (grid[row][col].isRevealed || grid[row][col].isFlagged) return
        if (grid[row][col].hasMine) {
            // Handle game over logic
        } else {
            revealCell(row, col)
        }
    }

    private fun revealCell(row: Int, col: Int) {
        if (row !in 0 until rows || col !in 0 until cols || grid[row][col].isRevealed) return
        grid[row][col].isRevealed = true
        if (grid[row][col].adjacentMines == 0) {
            for (dr in -1..1) {
                for (dc in -1..1) {
                    revealCell(row + dr, col + dc)
                }
            }
        }
    }

    val rowCount: Int
        get() = rows

    val columnCount: Int
        get() = cols
}