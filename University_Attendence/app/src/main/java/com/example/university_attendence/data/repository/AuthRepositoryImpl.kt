package com.example.university_attendence.data.repository

import com.example.university_attendence.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser == null) {
                trySend(null)
            } else {
                firestore.collection("users").document(firebaseUser.uid)
                    .addSnapshotListener { snapshot, _ ->
                        val user = snapshot?.toObject(User::class.java)
                        trySend(user)
                    }
            }
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun login(email: String, password: String): Result<User> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val firebaseUser = result.user ?: throw Exception("Login failed")
        val userDoc = firestore.collection("users").document(firebaseUser.uid).get().await()
        val user = userDoc.toObject(User::class.java) ?: throw Exception("User data not found")
        Result.success(user)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun register(user: User, password: String): Result<User> = try {
        val result = auth.createUserWithEmailAndPassword(user.email, password).await()
        val firebaseUser = result.user ?: throw Exception("Registration failed")
        val newUser = user.copy(uid = firebaseUser.uid)
        firestore.collection("users").document(firebaseUser.uid).set(newUser).await()
        Result.success(newUser)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun forgotPassword(email: String): Result<Unit> = try {
        auth.sendPasswordResetEmail(email).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
