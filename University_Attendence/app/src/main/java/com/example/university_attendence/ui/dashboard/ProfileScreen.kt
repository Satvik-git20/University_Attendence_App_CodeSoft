package com.example.university_attendence.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.university_attendence.ui.components.*
import com.example.university_attendence.ui.theme.*

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onBack: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSlate)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryBlue, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .padding(top = 48.dp, bottom = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), 
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                    }
                }
                
                // Initials Avatar Logic for Satvik
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color.White)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text("SA", color = Color.White, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Text("Satvik", color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("2021CS1045 • B.Tech CSE • Sem VI", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        // Academic Records Stats Row
        Row(
            modifier = Modifier.padding(horizontal = 24.dp), 
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileStatCard("Current CGPA", "8.92", Modifier.weight(1f))
            ProfileStatCard("Credits", "124", Modifier.weight(1f))
            ProfileStatCard("Attendance", "82%", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Details Sections
        ProfileInfoSection("Academic Information") {
            ProfileInfoItem(Icons.Default.Fingerprint, "Registration No", "REG-2021-00456")
            ProfileInfoItem(Icons.Default.CorporateFare, "Department", "Computer Science")
            ProfileInfoItem(Icons.Default.Email, "Official Email", "satvik@university.edu")
            ProfileInfoItem(Icons.Default.AccountCircle, "Academic Advisor", "Dr. Amit Sharma")
        }

        ProfileInfoSection("Account Management") {
            ProfileInfoItem(Icons.Default.Person, "Edit Profile", "Update your primary details")
            ProfileInfoItem(Icons.Default.Lock, "Change Password", "Secure your portal access")
            ProfileInfoItem(Icons.Default.Help, "Help & Support", "Get technical assistance")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ErrorRed.copy(alpha = 0.1f)),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null, tint = ErrorRed)
            Spacer(modifier = Modifier.width(12.dp))
            Text("Logout from Portal", color = ErrorRed, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Composable
fun ProfileStatCard(label: String, value: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontWeight = FontWeight.Black, fontSize = 20.sp, color = PrimaryBlue)
            Text(label, color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfileInfoSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
        Text(title, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = TextGray)
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                content()
            }
        }
    }
}

@Composable
fun ProfileInfoItem(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(PrimaryBlue.copy(alpha = 0.08f)), 
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = PrimaryBlue, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, color = TextGray, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold)
            Text(value, color = TextDark, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }
    }
}
