package com.example.university_attendence.data.model

data class AttendanceSession(
    val id: String = "",
    val courseId: String = "",
    val instructorId: String = "",
    val startTime: Long = 0L,
    val endTime: Long? = null,
    val isActive: Boolean = false,
    val qrCodeContent: String = "", // Unique token for this session's QR
    val latitude: Double? = null,
    val longitude: Double? = null,
    val radiusInMeters: Int? = null
)
