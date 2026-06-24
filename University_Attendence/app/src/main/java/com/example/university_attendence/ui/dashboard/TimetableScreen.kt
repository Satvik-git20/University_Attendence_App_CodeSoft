package com.example.university_attendence.ui.dashboard

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
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.university_attendence.data.model.TimetableSlot
import com.example.university_attendence.ui.components.*
import com.example.university_attendence.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen(onBack: () -> Unit) {
    var selectedDay by remember { mutableIntStateOf(1) } // 1 = Monday
    var searchQuery by remember { mutableStateOf("") }
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weekly Timetable", fontWeight = FontWeight.Bold) },
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
        Column(modifier = Modifier.padding(padding)) {
            // Search Subjects
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                placeholder = { Text("Search subjects...", color = TextGray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = PrimaryBlue) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            // Day Selector
            ScrollableTabRow(
                selectedTabIndex = selectedDay - 1,
                containerColor = Color.White,
                edgePadding = 24.dp,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedDay - 1]),
                        color = PrimaryBlue
                    )
                }
            ) {
                days.forEachIndexed { index, day ->
                    Tab(
                        selected = selectedDay == index + 1,
                        onClick = { selectedDay = index + 1 },
                        text = { Text(day, fontWeight = if (selectedDay == index + 1) FontWeight.Bold else FontWeight.Normal) },
                        selectedContentColor = PrimaryBlue,
                        unselectedContentColor = TextGray
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 20.dp, bottom = 100.dp)
            ) {
                val filteredSlots = dummyFullTimetable.filter { 
                    it.dayOfWeek == selectedDay && 
                    (searchQuery.isEmpty() || it.courseName.contains(searchQuery, ignoreCase = true))
                }

                if (filteredSlots.isEmpty()) {
                    item {
                        EmptyState(
                            icon = Icons.Default.EventBusy,
                            message = "No classes scheduled",
                            description = "There are no lectures for this day."
                        )
                    }
                } else {
                    items(filteredSlots) { slot ->
                        TimetableSlotAssistantCard(slot)
                    }
                }
            }
        }
    }
}

@Composable
fun TimetableSlotAssistantCard(slot: TimetableSlot) {
    val isLive = slot.status == "Live"
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = if(isLive) 4.dp else 1.dp),
        border = if(isLive) BorderStroke(2.dp, PrimaryBlue) else null
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(70.dp)) {
                Text(slot.startTime, fontWeight = FontWeight.Bold, color = if(isLive) PrimaryBlue else TextDark, fontSize = 14.sp)
                Text("to", color = TextGray, fontSize = 10.sp)
                Text(slot.endTime, fontWeight = FontWeight.Bold, color = if(isLive) PrimaryBlue else TextDark, fontSize = 14.sp)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            VerticalDivider(modifier = Modifier.height(50.dp), thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.5f))
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(slot.courseName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = TextDark)
                    if(isLive) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(SecondaryEmerald))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = TextGray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(slot.facultyName, color = TextGray, style = MaterialTheme.typography.bodySmall)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = TextGray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Room: ${slot.classroom}", color = TextGray, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

val dummyFullTimetable = listOf(
    TimetableSlot("1", 1, "09:00 AM", "10:00 AM", "CS101", "Data Structures", "Dr. Sharma", "LH-101", status = "Completed"),
    TimetableSlot("2", 1, "11:00 AM", "12:00 PM", "CS102", "Operating Systems", "Prof. Gupta", "LH-202", status = "Live"),
    TimetableSlot("3", 2, "10:00 AM", "11:00 AM", "CS103", "Database Systems", "Dr. Singh", "Lab-3", status = "Scheduled"),
    TimetableSlot("4", 3, "02:00 PM", "03:00 PM", "CS104", "Computer Networks", "Prof. Verma", "LH-104", status = "Scheduled")
)
