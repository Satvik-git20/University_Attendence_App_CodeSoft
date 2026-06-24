package com.example.university_attendence.ui.dashboard

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
import com.example.university_attendence.data.model.Notification
import com.example.university_attendence.ui.components.*
import com.example.university_attendence.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(onBack: () -> Unit) {
    var notifications by remember { mutableStateOf(dummyNotificationsList) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { 
                        notifications = notifications.map { it.copy(isRead = true) }
                    }) {
                        Text("Mark all read", color = PrimaryBlue, fontWeight = FontWeight.SemiBold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = BackgroundSlate
    ) { padding ->
        if (notifications.isEmpty()) {
            EmptyState(
                icon = Icons.Default.NotificationsOff,
                message = "All caught up!",
                description = "You have no new notifications."
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notifications) { notification ->
                    NotificationAssistantCard(notification)
                }
            }
        }
    }
}

@Composable
fun NotificationAssistantCard(notification: Notification) {
    val (icon, color) = when(notification.type) {
        "Attendance" -> Icons.Default.CheckCircle to SecondaryEmerald
        "Assignment" -> Icons.Default.Assignment to WarningAmber
        "Exam" -> Icons.Default.School to PrimaryBlue
        "Holiday" -> Icons.Default.BeachAccess to PrimaryBlue
        else -> Icons.Default.Campaign to PrimaryBlue
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(notification.isRead) Color.White else PrimaryBlue.copy(alpha = 0.04f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if(notification.isRead) 1.dp else 0.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(44.dp).clip(CircleShape).background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(notification.title, fontWeight = FontWeight.Bold, color = TextDark, fontSize = 15.sp)
                Text(notification.message, color = TextGray, style = MaterialTheme.typography.bodySmall, maxLines = 2)
            }
            if (!notification.isRead) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(PrimaryBlue))
            }
        }
    }
}

val dummyNotificationsList = listOf(
    Notification("1", "Attendance Marked", "Your attendance for Operating Systems has been updated.", isRead = false, type = "Attendance"),
    Notification("2", "New Assignment", "Prof. Gupta uploaded 'Process Synchronization' lab tasks.", isRead = false, type = "Assignment"),
    Notification("3", "Exam Schedule", "Final exam schedule for Semester VI is now available.", isRead = true, type = "Exam"),
    Notification("4", "Holiday Notice", "University will remain closed tomorrow on account of Holi.", isRead = true, type = "Holiday")
)
