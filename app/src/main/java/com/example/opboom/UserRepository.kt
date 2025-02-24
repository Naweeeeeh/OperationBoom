package com.example.opboom

class UserRepository(private val userDao: UserDao, private val gameProgressDao: GameProgressDao) {

    // Register a new user
    suspend fun registerUser(username: String, password: String): Long {
        val user = User(username = username, password = password)
        return userDao.insert(user)
    }

    // Login a user by checking username and password
    suspend fun loginUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }

    // Get a user by their username
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    // Get game progress for a user
    suspend fun getGameProgress(userId: Long): GameProgress? {
        return gameProgressDao.getGameProgress(userId)
    }

    // Insert game progress
    suspend fun insertGameProgress(gameProgress: GameProgress): Long {
        return gameProgressDao.insert(gameProgress)
    }

    // Update game progress
    suspend fun updateGameProgress(gameProgress: GameProgress) {
        gameProgressDao.update(gameProgress)
    }
}