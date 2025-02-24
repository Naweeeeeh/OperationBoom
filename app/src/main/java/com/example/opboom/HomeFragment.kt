package com.example.opboom

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeFragment : Fragment() {


    private lateinit var userRepository: UserRepository
    private var currentUser: User? = null
    private lateinit var gridLayout: GridLayout
    private lateinit var resultTextView: TextView
    private lateinit var resetButton: Button
    private lateinit var timerTextView: TextView

    private var width = 8
    private var height = 8
    private var mines = 10
    private lateinit var mineField: Array<IntArray>
    private lateinit var revealed: Array<BooleanArray>
    private lateinit var buttons: Array<Array<Button?>>
    private var flagMode = false

    private var startTime: Long = 0
    private val handler = Handler(Looper.getMainLooper())
    private val updateTimer = object : Runnable {
        override fun run() {
            val elapsedTime = (System.currentTimeMillis() - startTime) / 1000
            timerTextView.text = "Time: $elapsedTime"
            handler.postDelayed(this, 1000) // Update every second
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val database = AppDatabase.getDatabase(requireContext())
        userRepository = UserRepository(database.userDao(), database.gameProgressDao())
        gridLayout = view.findViewById(R.id.gridLayout)
        resultTextView = view.findViewById(R.id.resultTextView)
        resetButton = view.findViewById(R.id.resetButton)
        val flagButton = view.findViewById<Button>(R.id.flagButton)
        timerTextView = view.findViewById(R.id.timerTextView) // Initialize timerTextView

        resetButton.setOnClickListener {
            resetGame()
        }

        flagButton.setOnClickListener {
            flagMode = !flagMode
            flagButton.text = if (flagMode) "🚩 Flag (ON)" else "🚩 Flag (OFF)"
        }

        loadSettings()
        resetGame()

        startTimer() // Start the timer after everything is initialized

        lifecycleScope.launch {
            val userId = userRepository.registerUser("testUser", "testPassword")
            currentUser = userRepository.loginUser("testUser", "testPassword")
        }



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopTimer() // Stop the timer when the fragment is destroyed
    }

    private fun startTimer() {
        startTime = System.currentTimeMillis()
        handler.post(updateTimer)
    }

    private fun stopTimer() {
        handler.removeCallbacks(updateTimer)
    }

    private fun loadSettings() {
        val sharedPref = requireActivity().getSharedPreferences("MinesweeperSettings", Context.MODE_PRIVATE)
        width = sharedPref.getInt("width", 8)
        height = sharedPref.getInt("height", 8)
        mines = sharedPref.getInt("mines", 10)

        // Initialize arrays with the correct size
        mineField = Array(height) { IntArray(width) }
        revealed = Array(height) { BooleanArray(width) }
        buttons = Array(height) { arrayOfNulls<Button>(width) }
    }

    private fun resetGame() {
        stopTimer() // Stop the timer
        mineField = Array(height) { IntArray(width) }
        revealed = Array(height) { BooleanArray(width) }
        buttons = Array(height) { arrayOfNulls<Button>(width) }

        gridLayout.removeAllViews()
        gridLayout.columnCount = width
        gridLayout.rowCount = height

        placeMines()
        calculateNumbers()
        createGrid()

        resultTextView.visibility = View.GONE
        startTimer() // Restart the timer
    }

    private fun placeMines() {
        var minesPlaced = 0
        while (minesPlaced < mines) {
            val x = Random.nextInt(width)
            val y = Random.nextInt(height)
            if (mineField[y][x] != -1) {
                mineField[y][x] = -1
                minesPlaced++
            }
        }
    }

    private fun calculateNumbers() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (mineField[y][x] == -1) continue
                var count = 0
                for (dy in -1..1) {
                    for (dx in -1..1) {
                        if (dx == 0 && dy == 0) continue
                        val nx = x + dx
                        val ny = y + dy
                        if (nx in 0 until width && ny in 0 until height && mineField[ny][nx] == -1) {
                            count++
                        }
                    }
                }
                mineField[y][x] = count
            }
        }
    }

    private fun createGrid() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val button = Button(requireContext()).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 100
                        height = 100
                        columnSpec = GridLayout.spec(x)
                        rowSpec = GridLayout.spec(y)
                    }
                    setOnClickListener {
                        handleClick(x, y)
                    }
                }
                buttons[y][x] = button
                gridLayout.addView(button)
            }
        }
    }

    private fun handleClick(x: Int, y: Int) {
        if (flagMode) {
            // Toggle flag on the tile
            if (!revealed[y][x]) {
                buttons[y][x]?.text = if (buttons[y][x]?.text == "🚩") "" else "🚩"
            }
            return
        }

        if (revealed[y][x] || buttons[y][x]?.text == "🚩") return // Skip if the tile is already revealed or flagged
        revealed[y][x] = true // Mark the tile as revealed

        if (mineField[y][x] == -1) {
            // Tile is a mine
            buttons[y][x]?.text = "X"
            buttons[y][x]?.setBackgroundColor(Color.RED)
            gameOver()
        } else if (mineField[y][x] == 0) {
            // Tile is empty (no adjacent mines)
            buttons[y][x]?.text = ""
            buttons[y][x]?.setBackgroundColor(Color.LTGRAY)
            revealAdjacent(x, y)
        } else {
            // Tile has adjacent mines
            buttons[y][x]?.text = mineField[y][x].toString()
            buttons[y][x]?.setBackgroundColor(Color.LTGRAY)
        }

        checkWin()
    }

    private fun revealAdjacent(x: Int, y: Int) {
        for (dy in -1..1) {
            for (dx in -1..1) {
                if (dx == 0 && dy == 0) continue // Skip the current tile
                val nx = x + dx
                val ny = y + dy
                if (nx in 0 until width && ny in 0 until height && !revealed[ny][nx]) {
                    handleClick(nx, ny) // Recursively reveal adjacent tiles
                }
            }
        }
    }

    private fun gameOver() {
        stopTimer() // Stop the timer
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (mineField[y][x] == -1) {
                    buttons[y][x]?.text = "X"
                }
            }
        }
        resultTextView.text = "Game Over! Time: ${(System.currentTimeMillis() - startTime) / 1000} sec"
        resultTextView.visibility = View.VISIBLE
    }

    private fun checkWin() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (!revealed[y][x] && mineField[y][x] != -1) return
            }
        }
        stopTimer() // Stop the timer
        resultTextView.text = "Congratulations! Time: ${(System.currentTimeMillis() - startTime) / 1000} sec"
        resultTextView.visibility = View.VISIBLE
    }

    private fun saveGameProgress(time: Long) {
        lifecycleScope.launch {
            val userId = currentUser?.id ?: return@launch
            var gameProgress = userRepository.getGameProgress(userId)
            if (gameProgress == null) {
                gameProgress = GameProgress(userId = userId, bestTime = time, gamesPlayed = 1)
                userRepository.insertGameProgress(gameProgress)
            } else {
                if (time < gameProgress.bestTime) {
                    gameProgress.bestTime = time
                }
                gameProgress.gamesPlayed++
                userRepository.updateGameProgress(gameProgress)
            }
        }
    }

    private fun loadGameProgress() {
        lifecycleScope.launch {
            val userId = currentUser?.id ?: return@launch
            val gameProgress = userRepository.getGameProgress(userId)
            if (gameProgress != null) {
                // Display best time and games played
                resultTextView.text = "Best Time: ${gameProgress.bestTime} sec\nGames Played: ${gameProgress.gamesPlayed}"
            }
        }
    }
}