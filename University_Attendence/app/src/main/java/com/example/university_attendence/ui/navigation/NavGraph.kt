package com.example.university_attendence.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.university_attendence.data.repository.AuthRepository
import com.example.university_attendence.ui.auth.LoginScreen
import com.example.university_attendence.ui.auth.RegisterScreen
import com.example.university_attendence.ui.dashboard.*
import com.example.university_attendence.ui.attendance.QRScannerScreen
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object StudentDashboard : Screen("student_dashboard")
    object InstructorDashboard : Screen("instructor_dashboard")
    object Timetable : Screen("timetable")
    object Courses : Screen("courses")
    object Analytics : Screen("analytics")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object Notifications : Screen("notifications")
    object QRScanner : Screen("qr_scanner")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    authRepository: AuthRepository,
    startDestination: String = Screen.Login.route
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.StudentDashboard.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.StudentDashboard.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.StudentDashboard.route) {
            StudentDashboardScreen(
                onNavigateToAnalytics = { navController.navigate(Screen.Analytics.route) },
                onNavigateToTimetable = { navController.navigate(Screen.Timetable.route) },
                onNavigateToCourses = { navController.navigate(Screen.Courses.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onScanQR = { navController.navigate(Screen.QRScanner.route) }
            )
        }
        composable(Screen.Timetable.route) {
            TimetableScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Courses.route) {
            CoursesScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Analytics.route) {
            AnalyticsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onLogout = {
                    scope.launch {
                        authRepository.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                onBack = { navController.popBackStack() },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Notifications.route) {
            NotificationScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.InstructorDashboard.route) {
            InstructorDashboardScreen(
                onCreateCourse = {
                    // Navigate to create course screen
                }
            )
        }
        composable(Screen.QRScanner.route) {
            QRScannerScreen(
                onScanSuccess = { result ->
                    // Handle scan result
                    navController.popBackStack()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
