package com.example.university_attendence.ui.dashboard

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSlate)
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(
            title = { Text("Academic Analytics", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        Column(modifier = Modifier.padding(24.dp)) {
            OverallHealthCard()
            Spacer(modifier = Modifier.height(28.dp))
            
            SectionHeader("Smart Prediction")
            PredictionPanel()
            
            Spacer(modifier = Modifier.height(28.dp))
            SectionHeader("Subject Comparison")
            SubjectComparisonChart()
            
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun OverallHealthCard() {
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(targetValue = progress, animationSpec = tween(1500))
    LaunchedEffect(Unit) { progress = 0.82f }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(160.dp)) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryBlue,
                    strokeWidth = 14.dp,
                    trackColor = PrimaryBlue.copy(alpha = 0.1f),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${(animatedProgress * 100).toInt()}%", fontWeight = FontWeight.Black, fontSize = 36.sp, color = TextDark)
                    Text("Overall Health", color = TextGray, fontSize = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                AnalyticsStat("Present", "42", SecondaryEmerald)
                AnalyticsStat("Absent", "8", ErrorRed)
                AnalyticsStat("Required", "75%", TextDark)
            }
        }
    }
}

@Composable
fun AnalyticsStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Black, fontSize = 20.sp, color = color)
        Text(label, color = TextGray, fontSize = 11.sp)
    }
}

@Composable
fun PredictionPanel() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            PredictionRow("Maintain 75%", "Already achieved", SecondaryEmerald, 1f)
            Spacer(modifier = Modifier.height(16.dp))
            PredictionRow("Reach 85%", "Attend next 5 classes", WarningAmber, 0.6f)
            Spacer(modifier = Modifier.height(16.dp))
            PredictionRow("Reach 90%", "Attend next 12 classes", ErrorRed, 0.4f)
        }
    }
}

@Composable
fun PredictionRow(target: String, message: String, color: Color, progress: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(target, fontWeight = FontWeight.Bold, color = TextDark, fontSize = 14.sp)
            Text(message, color = color, fontSize = 11.sp, fontWeight = FontWeight.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
            color = color,
            trackColor = Color(0xFFF1F5F9),
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )
    }
}

@Composable
fun SubjectComparisonChart() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            val stats = listOf(
                "Data Structures" to 0.90f,
                "Operating Systems" to 0.60f,
                "Database Systems" to 0.85f,
                "Computer Networks" to 0.78f
            )
            stats.forEach { (subject, score) ->
                SubjectProgressRow(subject, score)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SubjectProgressRow(subject: String, score: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(subject, style = MaterialTheme.typography.bodyMedium, color = TextDark, fontWeight = FontWeight.Medium)
            Text("${(score * 100).toInt()}%", fontWeight = FontWeight.Black, color = if(score >= 0.75f) PrimaryBlue else ErrorRed, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { score },
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
            color = if(score >= 0.75f) PrimaryBlue else ErrorRed,
            trackColor = Color(0xFFF1F5F9)
        )
    }
}
