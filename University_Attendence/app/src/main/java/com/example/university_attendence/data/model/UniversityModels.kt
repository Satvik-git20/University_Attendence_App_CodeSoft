package com.example.university_attendence.data.model

import com.google.firebase.Timestamp

data class Student(
    val uid: String = "",
    val name: String = "Satvik", // Updated name to Satvik
    val email: String = "",
    val rollNumber: String = "",
    val registrationNumber: String = "",
    val universityId: String = "",
    val branch: String = "",
    val semester: Int = 1,
    val section: String = "A",
    val department: String = "",
    val profileImageUrl: String? = null,
    val currentGpa: Double = 0.0,
    val creditsEarned: Int = 0,
    val academicAdvisor: String = ""
)

data class Faculty(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val employeeId: String = "",
    val department: String = "",
    val designation: String = ""
)

data class Course(
    val id: String = "",
    val code: String = "",
    val name: String = "",
    val credits: Int = 0,
    val facultyId: String = "",
    val facultyName: String = "",
    val department: String = "",
    val syllabus: List<String> = emptyList(),
    val instructorId: String = "", 
    val studentIds: List<String> = emptyList(),
    val attendancePercentage: Int = 0,
    val totalClasses: Int = 0,
    val presentClasses: Int = 0,
    val absentClasses: Int = 0,
    val status: String = "Ongoing"
)

data class AttendanceRecord(
    val id: String = "",
    val studentId: String = "",
    val courseId: String = "",
    val courseName: String = "",
    val date: Timestamp = Timestamp.now(),
    val timestamp: Long = 0L,
    val status: AttendanceStatus = AttendanceStatus.PRESENT,
    val classroom: String = ""
)

enum class AttendanceStatus {
    PRESENT, ABSENT, LATE, HOLIDAY
}

data class Assignment(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val subjectId: String = "",
    val subjectName: String = "",
    val dueDate: Timestamp = Timestamp.now(),
    val status: String = "Pending", // Pending, Submitted, Graded
    val priority: String = "Medium", // High, Medium, Low
    val submissionUrl: String? = null
)

data class Announcement(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val date: Timestamp = Timestamp.now(),
    val authorName: String = "",
    val type: AnnouncementType = AnnouncementType.GENERAL
)

enum class AnnouncementType {
    GENERAL, EXAM, HOLIDAY, ASSIGNMENT, FACULTY
}

data class TimetableSlot(
    val id: String = "",
    val dayOfWeek: Int = 1,
    val startTime: String = "",
    val endTime: String = "",
    val courseId: String = "",
    val courseName: String = "",
    val facultyName: String = "",
    val classroom: String = "",
    val isLive: Boolean = false,
    val status: String = "Scheduled"
)

data class Notification(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val isRead: Boolean = false,
    val type: String = "General"
)

data class DailySummary(
    val classesToday: Int = 0,
    val pendingAssignments: Int = 0,
    val unreadNotifications: Int = 0,
    val nextExamSubject: String? = null,
    val daysUntilNextExam: Int? = null
)

data class ExamInfo(
    val id: String = "",
    val subject: String = "",
    val date: String = "",
    val time: String = "",
    val room: String = "",
    val faculty: String = ""
)

data class AttendanceAnalytics(
    val overallPercentage: Int = 0,
    val totalClasses: Int = 0,
    val attendedClasses: Int = 0,
    val absentClasses: Int = 0,
    val lateClasses: Int = 0,
    val subjectWisePercentage: Map<String, Int> = emptyMap(),
    val attendanceRequired: Int = 75,
    val targetPercentage: Int = 85,
    val classesNeededForTarget: Int = 0,
    val safetyStatus: String = "Safe Zone" // Safe Zone, Warning, Critical
)
