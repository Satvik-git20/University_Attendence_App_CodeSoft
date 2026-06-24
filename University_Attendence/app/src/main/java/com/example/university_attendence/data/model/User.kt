package com.example.university_attendence.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: UserRole = UserRole.STUDENT,
    val universityId: String? = null,
    val rollNumber: String? = null,
    val facultyId: String? = null,
    val department: String = "",
    val semester: String? = null,
    val profileImageUrl: String? = null
)

enum class UserRole {
    STUDENT, INSTRUCTOR, ADMIN
}
