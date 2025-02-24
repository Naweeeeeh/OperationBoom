package com.example.opboom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_progress")
data class GameProgress(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long, // Foreign key to User table
    var bestTime: Long, // Best time in seconds
    var gamesPlayed: Int
)