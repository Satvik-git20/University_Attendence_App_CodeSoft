package com.example.university_attendence.data.repository

import com.example.university_attendence.data.model.User
import com.example.university_attendence.data.model.UserRole
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(user: User, password: String): Result<User>
    suspend fun logout()
    suspend fun forgotPassword(email: String): Result<Unit>
}
