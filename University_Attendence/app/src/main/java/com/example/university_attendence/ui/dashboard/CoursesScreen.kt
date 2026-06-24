package com.example.university_attendence.ui.dashboard

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.university_attendence.data.model.Course
import com.example.university_attendence.ui.components.*
import com.example.university_attendence.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Courses", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = BackgroundSlate
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dummyFullCoursesList) { course ->
                CourseERPCard(course)
            }
            
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
fun CourseERPCard(course: Course) {
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "course_progress"
    )
    LaunchedEffect(Unit) { progress = course.attendancePercentage / 100f }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = PrimaryBlue.copy(alpha = 0.1f)
                ) {
                    Icon(Icons.Default.MenuBook, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.padding(12.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(course.code, color = PrimaryBlue, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Text(course.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TextDark)
                }
                Text("${course.credits} Credits", color = TextGray, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold)
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Attendance Progress", color = TextDark, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                Text("${course.attendancePercentage}%", fontWeight = FontWeight.Black, color = if(course.attendancePercentage >= 75) SecondaryEmerald else ErrorRed)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape),
                color = if(course.attendancePercentage >= 75) SecondaryEmerald else ErrorRed,
                trackColor = Color(0xFFF1F5F9),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Resources", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.4f)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Syllabus", fontSize = 12.sp, color = TextDark, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

val dummyFullCoursesList = listOf(
    Course("1", "CS101", "Data Structures", 4, "f1", "Dr. Sharma", "CSE", emptyList(), "", emptyList(), 90, 20, 18, 2, "Ongoing"),
    Course("2", "CS102", "Operating Systems", 4, "f2", "Prof. Gupta", "CSE", emptyList(), "", emptyList(), 60, 20, 12, 8, "Ongoing"),
    Course("3", "CS103", "Database Management", 3, "f3", "Dr. Singh", "CSE", emptyList(), "", emptyList(), 85, 20, 17, 3, "Ongoing"),
    Course("4", "CS104", "Computer Networks", 4, "f4", "Prof. Verma", "CSE", emptyList(), "", emptyList(), 78, 20, 15, 5, "Ongoing")
)
