package com.example.university_attendence.data.repository

import com.example.university_attendence.data.model.AttendanceRecord
import com.example.university_attendence.data.model.AttendanceSession
import com.example.university_attendence.data.model.Course
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AttendanceRepository {

    override fun getCoursesForUser(userId: String, isInstructor: Boolean): Flow<List<Course>> = callbackFlow {
        val query = if (isInstructor) {
            firestore.collection("courses").whereEqualTo("instructorId", userId)
        } else {
            firestore.collection("courses").whereArrayContains("studentIds", userId)
        }

        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            val courses = snapshot?.toObjects(Course::class.java) ?: emptyList()
            trySend(courses)
        }
        awaitClose { listener.remove() }
    }

    override suspend fun createCourse(course: Course): Result<Unit> = try {
        val docRef = firestore.collection("courses").document()
        val newCourse = course.copy(id = docRef.id)
        docRef.set(newCourse).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun startAttendanceSession(session: AttendanceSession): Result<String> = try {
        val docRef = firestore.collection("attendance_sessions").document()
        val newSession = session.copy(id = docRef.id, isActive = true, startTime = System.currentTimeMillis())
        docRef.set(newSession).await()
        Result.success(docRef.id)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun endAttendanceSession(sessionId: String): Result<Unit> = try {
        firestore.collection("attendance_sessions").document(sessionId)
            .update("isActive", false, "endTime", System.currentTimeMillis())
            .await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getActiveSession(courseId: String): Flow<AttendanceSession?> = callbackFlow {
        val listener = firestore.collection("attendance_sessions")
            .whereEqualTo("courseId", courseId)
            .whereEqualTo("isActive", true)
            .limit(1)
            .addSnapshotListener { snapshot, _ ->
                val session = snapshot?.toObjects(AttendanceSession::class.java)?.firstOrNull()
                trySend(session)
            }
        awaitClose { listener.remove() }
    }

    override suspend fun markAttendance(record: AttendanceRecord): Result<Unit> = try {
        val docRef = firestore.collection("attendance_records").document()
        val newRecord = record.copy(id = docRef.id, timestamp = System.currentTimeMillis())
        docRef.set(newRecord).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getAttendanceHistory(userId: String, courseId: String?): Flow<List<AttendanceRecord>> = callbackFlow {
        var query = firestore.collection("attendance_records")
            .whereEqualTo("studentId", userId)
        
        if (courseId != null) {
            query = query.whereEqualTo("courseId", courseId)
        }
        
        val listener = query.orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                val records = snapshot?.toObjects(AttendanceRecord::class.java) ?: emptyList()
                trySend(records)
            }
        awaitClose { listener.remove() }
    }
}
