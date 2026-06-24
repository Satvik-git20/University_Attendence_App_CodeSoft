package com.example.university_attendence.ui.dashboard

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.university_attendence.data.model.*
import com.example.university_attendence.ui.components.*
import com.example.university_attendence.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScreen(
    onNavigateToAnalytics: () -> Unit,
    onNavigateToTimetable: () -> Unit,
    onNavigateToCourses: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onScanQR: () -> Unit
) {
    var selectedRoute by remember { mutableStateOf("student_dashboard") }
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            ModernBottomNavigation(
                selectedRoute = selectedRoute,
                onRouteSelected = { route ->
                    selectedRoute = route
                    when(route) {
                        "analytics" -> onNavigateToAnalytics()
                        "timetable" -> onNavigateToTimetable()
                        "courses" -> onNavigateToCourses()
                        "profile" -> onNavigateToProfile()
                    }
                }
            )
        },
        containerColor = BackgroundSlate
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                // Simulate Firestore fetch
                scope.launch {
                    kotlinx.coroutines.delay(1500)
                    isRefreshing = false
                }
            },
            modifier = Modifier.padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                item { PersonalizedHeroSection(onNotificationsClick = onNavigateToNotifications) }
                
                item { NextClassHighPriorityCard() }

                item { 
                    AttendanceAssistantCard(
                        percentage = 82,
                        present = 42,
                        absent = 8,
                        required = 75,
                        classesNeededFor85 = 4,
                        onViewDetails = onNavigateToAnalytics
                    ) 
                }

                item { DailySummaryCard() }

                item { 
                    QuickActionSection(
                        onScanQR = onScanQR, 
                        onTimetable = onNavigateToTimetable
                    ) 
                }

                item { SmartInsightsSection() }
                
                item { SectionHeader("Today's Timetable", onSeeAll = onNavigateToTimetable) }
                items(dummyTimetableSlots) { slot: TimetableSlot ->
                    DashboardTimetableItem(slot, onMarkAttendance = onScanQR)
                }
                
                item { SectionHeader("University Announcements", onSeeAll = {}) }
                item { AnnouncementsERPRow() }
                
                item { SectionHeader("Upcoming Assignments", onSeeAll = {}) }
                items(dummyAssignments) { assignment: Assignment ->
                    AssignmentAssistantCard(assignment)
                }

                item { SectionHeader("Subject-wise Attendance", onSeeAll = onNavigateToCourses) }
                items(dummyCourses) { course: Course ->
                    SubjectAttendanceERPCard(course)
                }

                item { SectionHeader("Upcoming Exams", onSeeAll = {}) }
                items(dummyExams) { exam: ExamInfo ->
                    ExamAssistantCard(exam)
                }
            }
        }
    }
}

@Composable
fun PersonalizedHeroSection(onNotificationsClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PrimaryBlue, PrimaryBlue.copy(alpha = 0.8f))
                ),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
            .padding(24.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Good Morning, Satvik 👋", color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("Monday, 24 June • 3 classes today", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodyMedium)
                }
                
                IconButton(
                    onClick = onNotificationsClick,
                    modifier = Modifier.clip(CircleShape).background(Color.White.copy(alpha = 0.15f))
                ) {
                    BadgedBox(badge = { Badge(containerColor = ErrorRed) { Text("2") } }) {
                        Icon(Icons.Outlined.Notifications, contentDescription = null, tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun NextClassHighPriorityCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .offset(y = (-60).dp)
            .shadow(16.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = PrimaryBlue.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Timer, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.padding(12.dp).size(28.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Next Class", color = TextGray, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Text("Operating Systems", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = TextDark)
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                InfoIconLabel(Icons.Default.Person, "Prof. Gupta")
                InfoIconLabel(Icons.Default.LocationOn, "Room LH-202")
                InfoIconLabel(Icons.Default.Schedule, "11:00 AM")
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryBlue.copy(alpha = 0.05f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Starts in ", color = TextDark, style = MaterialTheme.typography.bodyMedium)
                    Text("18 minutes", color = PrimaryBlue, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
fun AttendanceAssistantCard(percentage: Int, present: Int, absent: Int, required: Int, classesNeededFor85: Int, onViewDetails: () -> Unit) {
    val statusColor = if(percentage >= 85) SecondaryEmerald else if(percentage >= 75) WarningAmber else ErrorRed
    
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 24.dp).shadow(8.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(100.dp)) {
                    CircularProgressIndicator(
                        progress = { percentage / 100f },
                        modifier = Modifier.fillMaxSize(),
                        color = statusColor,
                        strokeWidth = 10.dp,
                        trackColor = statusColor.copy(alpha = 0.1f),
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("$percentage%", fontWeight = FontWeight.Black, fontSize = 24.sp, color = statusColor)
                        Text("Overall", color = TextGray, fontSize = 10.sp)
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column {
                    Text("Attendance Health", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                    Row(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text("✅ Safe Zone", color = SecondaryEmerald, fontWeight = FontWeight.Black, fontSize = 14.sp)
                    }
                    Text("Need only $classesNeededFor85 more classes to reach 85%", color = TextGray, style = MaterialTheme.typography.labelSmall)
                    
                    TextButton(onClick = onViewDetails, contentPadding = PaddingValues(0.dp)) {
                        Text("View detailed analytics", color = PrimaryBlue, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Icon(Icons.Default.ChevronRight, null, tint = PrimaryBlue, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DailySummaryCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SummaryItem(
                icon = Icons.Default.School,
                label = "Classes",
                value = "3",
                color = PrimaryBlue,
                modifier = Modifier.weight(1f)
            )
            VerticalDivider(modifier = Modifier.height(30.dp), thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.3f))
            SummaryItem(
                icon = Icons.Default.Assignment,
                label = "Assignments",
                value = "1",
                color = WarningAmber,
                modifier = Modifier.weight(1f)
            )
            VerticalDivider(modifier = Modifier.height(30.dp), thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.3f))
            SummaryItem(
                icon = Icons.Default.Email,
                label = "Inbox",
                value = "2",
                color = ErrorRed,
                modifier = Modifier.weight(1f)
            )
            VerticalDivider(modifier = Modifier.height(30.dp), thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.3f))
            SummaryItem(
                icon = Icons.Default.Event,
                label = "Next Exam",
                value = "3d",
                color = SecondaryEmerald,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SummaryItem(
    icon: ImageVector,
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color.copy(alpha = 0.8f),
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = TextDark,
            maxLines = 1
        )
        Text(
            text = label,
            color = TextGray,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )
    }
}

@Composable
fun QuickActionSection(onScanQR: () -> Unit, onTimetable: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuickActionCard("Scan QR", Icons.Default.QrCodeScanner, PrimaryBlue, Modifier.weight(1f), onScanQR)
        QuickActionCard("Timetable", Icons.Default.CalendarToday, WarningAmber, Modifier.weight(1f), onTimetable)
        QuickActionCard("Leave", Icons.Default.ExitToApp, ErrorRed, Modifier.weight(1f), {})
        QuickActionCard("Assignments", Icons.Default.Assignment, SecondaryEmerald, Modifier.weight(1f), {})
    }
}

@Composable
fun QuickActionCard(label: String, icon: ImageVector, color: Color, modifier: Modifier, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "scale")

    Surface(
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(color.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextDark)
        }
    }
}

@Composable
fun AnnouncementsERPRow() {
    val dummyAnnouncements = listOf(
        Announcement("1", "Exam Schedule Released", "Final exam timetable for Sem VI is now available."),
        Announcement("2", "Holiday Tomorrow", "University will remain closed for Holi celebrations."),
        Announcement("3", "Semester Registration", "Academic portal open for next semester registration.")
    )
    LazyRow(contentPadding = PaddingValues(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(dummyAnnouncements) { ann ->
            Card(
                modifier = Modifier.width(300.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlue.copy(alpha = 0.05f))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Campaign, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(ann.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = TextDark)
                        Text(ann.content, color = TextGray, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}

@Composable
fun SmartInsightsSection() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryBlue.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Smart Insights", fontWeight = FontWeight.Bold, color = TextDark)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Your attendance improved by 3% this week.", color = TextGray, style = MaterialTheme.typography.bodySmall)
            Text("• Attend the next 4 Operating Systems classes to stay above 75%.", color = TextGray, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun DashboardTimetableItem(slot: TimetableSlot, onMarkAttendance: () -> Unit) {
    val isNext = slot.startTime == "11:00 AM" 
    
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        color = if(isNext) PrimaryBlue.copy(alpha = 0.05f) else Color.White,
        border = if(isNext) BorderStroke(2.dp, PrimaryBlue) else BorderStroke(1.dp, Color(0xFFF1F5F9))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(60.dp)) {
                Text(slot.startTime.split(" ")[0], fontWeight = FontWeight.Bold, color = if(isNext) PrimaryBlue else TextDark, fontSize = 16.sp)
                Text(slot.startTime.split(" ").getOrElse(1) { "" }, color = TextGray, fontSize = 10.sp)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(slot.courseName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = TextDark)
                Text("Prof. ${slot.facultyName} • Room ${slot.classroom}", color = TextGray, style = MaterialTheme.typography.bodySmall)
            }

            if (isNext) {
                Button(
                    onClick = onMarkAttendance,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("Mark", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AssignmentAssistantCard(assignment: Assignment) {
    val isUrgent = true 
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 6.dp),
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = BorderStroke(1.dp, if(isUrgent) ErrorRed.copy(alpha = 0.3f) else Color(0xFFF1F5F9))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).background(if(isUrgent) ErrorRed.copy(alpha = 0.1f) else WarningAmber.copy(alpha = 0.1f), RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Assignment, contentDescription = null, tint = if(isUrgent) ErrorRed else WarningAmber, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(assignment.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = TextDark)
                Text(assignment.subjectName, color = PrimaryBlue, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(if(isUrgent) "Due Today" else "Mar 26", color = if(isUrgent) ErrorRed else TextGray, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Text("Pending", color = TextGray, fontSize = 9.sp)
            }
        }
    }
}

@Composable
fun ExamAssistantCard(exam: ExamInfo) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 6.dp),
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFF1F5F9))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(60.dp)) {
                Text(exam.date.split(" ")[0], color = TextGray, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Text(exam.date.split(" ")[1], fontWeight = FontWeight.Black, fontSize = 20.sp, color = PrimaryBlue)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(exam.subject, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, color = TextDark)
                Text("LH-101 • 10:00 AM", color = TextGray, style = MaterialTheme.typography.bodySmall)
            }
            Surface(color = SecondaryEmerald.copy(alpha = 0.1f), shape = CircleShape) {
                Text("3 Days", color = SecondaryEmerald, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontWeight = FontWeight.Black, fontSize = 9.sp)
            }
        }
    }
}

@Composable
fun InfoIconLabel(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = TextGray, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, color = TextGray, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun SubjectAttendanceERPCard(course: Course) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(course.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge, color = TextDark)
                    Text("Prof. ${course.facultyName} • ${course.credits} Credits", color = TextGray, style = MaterialTheme.typography.labelSmall)
                }
                Text("${course.attendancePercentage}%", fontWeight = FontWeight.Bold, color = if(course.attendancePercentage >= 75) SecondaryEmerald else ErrorRed)
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { course.attendancePercentage / 100f },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                color = if(course.attendancePercentage >= 75) SecondaryEmerald else ErrorRed,
                trackColor = Color(0xFFF1F5F9),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("${course.presentClasses}/${course.totalClasses} Classes Present", color = TextGray, style = MaterialTheme.typography.labelSmall)
        }
    }
}

val dummyTimetableSlots = listOf(
    TimetableSlot("1", 1, "09:00 AM", "10:00 AM", "CS101", "Data Structures", "Dr. Sharma", "LH-101"),
    TimetableSlot("2", 1, "11:00 AM", "12:00 PM", "CS102", "Operating Systems", "Prof. Gupta", "LH-202")
)

val dummyCourses = listOf(
    Course("1", "CS101", "Data Structures", 4, "f1", "Dr. Sharma", "CSE", emptyList(), "", emptyList(), 90, 20, 18, 2, "Ongoing"),
    Course("2", "CS102", "Operating Systems", 4, "f2", "Prof. Gupta", "CSE", emptyList(), "", emptyList(), 60, 20, 12, 8, "Ongoing")
)

val dummyAssignments = listOf(
    Assignment("1", "Graph Algorithms", "", "CS101", "Data Structures", status = "Pending"),
    Assignment("2", "Process Scheduling", "", "CS102", "Operating Systems", status = "Pending")
)

val dummyExams = listOf(
    ExamInfo("1", "DBMS", "JUN 28", "10:00 AM", "LH-101", "Dr. Singh")
)
