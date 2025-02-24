package com.example.opboom



import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameProgressDao {
    @Insert
    suspend fun insert(gameProgress: GameProgress): Long

    @Update
    suspend fun update(gameProgress: GameProgress)

    @Query("SELECT * FROM game_progress WHERE userId = :userId")
    suspend fun getGameProgress(userId: Long): GameProgress?
}