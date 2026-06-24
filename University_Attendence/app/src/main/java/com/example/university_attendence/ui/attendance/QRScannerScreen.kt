package com.example.university_attendence.ui.attendance

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.university_attendence.ui.theme.PrimaryBlue

@Composable
fun QRScannerScreen(
    onScanSuccess: (String) -> Unit,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Full screen camera placeholder (Background)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Camera Preview", color = Color.LightGray)
        }

        // Overlay with frame
        ScannerOverlay()

        // Top UI
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.3f))
            ) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = null, tint = Color.White)
            }
            
            Text(
                "Scan Attendance",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            
            Spacer(modifier = Modifier.size(48.dp)) // Placeholder to balance
        }

        // Bottom Capture Button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            IconButton(
                onClick = { onScanSuccess("MOCK_QR_DATA") },
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue)
                    .padding(16.dp)
            ) {
                Icon(
                    Icons.Default.QrCodeScanner,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun ScannerOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val frameSize = 250.dp.toPx()
        val left = (width - frameSize) / 2
        val top = (height - frameSize) / 2
        val cornerLength = 40.dp.toPx()
        val strokeWidth = 4.dp.toPx()

        // Draw corners
        val path = Path().apply {
            // Top Left
            moveTo(left, top + cornerLength)
            lineTo(left, top)
            lineTo(left + cornerLength, top)

            // Top Right
            moveTo(left + frameSize - cornerLength, top)
            lineTo(left + frameSize, top)
            lineTo(left + frameSize, top + cornerLength)

            // Bottom Right
            moveTo(left + frameSize, top + frameSize - cornerLength)
            lineTo(left + frameSize, top + frameSize)
            lineTo(left + frameSize - cornerLength, top + frameSize)

            // Bottom Left
            moveTo(left + cornerLength, top + frameSize)
            lineTo(left, top + frameSize)
            lineTo(left, top + frameSize - cornerLength)
        }

        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = strokeWidth)
        )
        
        // Horizontal scan line
        drawLine(
            color = PrimaryBlue,
            start = androidx.compose.ui.geometry.Offset(left, height / 2),
            end = androidx.compose.ui.geometry.Offset(left + frameSize, height / 2),
            strokeWidth = 2.dp.toPx()
        )
    }
}
