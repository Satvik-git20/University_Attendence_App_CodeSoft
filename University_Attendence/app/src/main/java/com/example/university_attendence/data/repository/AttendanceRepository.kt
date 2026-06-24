package com.example.university_attendence.data.repository

import com.example.university_attendence.data.model.AttendanceRecord
import com.example.university_attendence.data.model.AttendanceSession
import com.example.university_attendence.data.model.Course
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {
    // Courses
    fun getCoursesForUser(userId: String, isInstructor: Boolean): Flow<List<Course>>
    suspend fun createCourse(course: Course): Result<Unit>
    
    // Sessions
    suspend fun startAttendanceSession(session: AttendanceSession): Result<String>
    suspend fun endAttendanceSession(sessionId: String): Result<Unit>
    fun getActiveSession(courseId: String): Flow<AttendanceSession?>
    
    // Records
    suspend fun markAttendance(record: AttendanceRecord): Result<Unit>
    fun getAttendanceHistory(userId: String, courseId: String? = null): Flow<List<AttendanceRecord>>
}
