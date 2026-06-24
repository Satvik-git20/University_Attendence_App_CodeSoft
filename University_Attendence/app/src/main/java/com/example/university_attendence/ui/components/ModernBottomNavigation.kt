package com.example.university_attendence.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.university_attendence.ui.theme.PrimaryBlue
import com.example.university_attendence.ui.theme.TextGray

@Composable
fun ModernBottomNavigation(
    selectedRoute: String,
    onRouteSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .height(76.dp)
                .fillMaxWidth()
                .shadow(24.dp, RoundedCornerShape(38.dp)),
            shape = RoundedCornerShape(38.dp),
            color = Color.White
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationItem(
                    label = "Home",
                    icon = if (selectedRoute == "student_dashboard") Icons.Filled.Home else Icons.Outlined.Home,
                    isSelected = selectedRoute == "student_dashboard",
                    onClick = { onRouteSelected("student_dashboard") }
                )
                NavigationItem(
                    label = "Time",
                    icon = if (selectedRoute == "timetable") Icons.Filled.CalendarMonth else Icons.Outlined.CalendarMonth,
                    isSelected = selectedRoute == "timetable",
                    onClick = { onRouteSelected("timetable") }
                )
                NavigationItem(
                    label = "Course",
                    icon = if (selectedRoute == "courses") Icons.AutoMirrored.Filled.MenuBook else Icons.AutoMirrored.Outlined.MenuBook,
                    isSelected = selectedRoute == "courses",
                    onClick = { onRouteSelected("courses") }
                )
                NavigationItem(
                    label = "Data",
                    icon = if (selectedRoute == "analytics") Icons.Filled.BarChart else Icons.Outlined.BarChart,
                    isSelected = selectedRoute == "analytics",
                    onClick = { onRouteSelected("analytics") }
                )
                NavigationItem(
                    label = "User",
                    icon = if (selectedRoute == "profile") Icons.Filled.Person else Icons.Outlined.Person,
                    isSelected = selectedRoute == "profile",
                    onClick = { onRouteSelected("profile") }
                )
            }
        }
    }
}

@Composable
private fun NavigationItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val animatedScale by animateFloatAsState(if (isSelected) 1.15f else 1.0f, label = "scale")
    val tint by animateColorAsState(if (isSelected) PrimaryBlue else TextGray.copy(alpha = 0.6f), label = "color")

    Box(
        modifier = Modifier
            .height(56.dp)
            .clip(CircleShape)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tint,
                modifier = Modifier
                    .size(26.dp)
                    .graphicsLayer(scaleX = animatedScale, scaleY = animatedScale)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                label,
                color = tint,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
