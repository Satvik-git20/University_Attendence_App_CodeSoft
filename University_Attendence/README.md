# University Attendance Management Application

A modern, production-ready University Attendance Management Application for Android, designed with a premium SaaS-like aesthetic.

## Features

- **Multi-role Support**: Dedicated dashboards for Students and Instructors.
- **Modern UI/UX**: Built with Jetpack Compose following Material Design 3 guidelines.
- **Firebase Integration**: Secure Authentication, Firestore Database, and Cloud Storage.
- **Attendance Module**: QR Code scanning and Face Recognition (Stubs) for attendance marking.
- **Analytics**: Beautiful charts and statistics for attendance trends.
- **Responsive Design**: Clean and elegant layout with smooth animations.

## Technology Stack

- **Frontend**: Kotlin + Jetpack Compose
- **Backend**: Firebase (Auth, Firestore, Storage)
- **Dependency Injection**: Hilt
- **Image Loading**: Coil
- **Charts**: MPAndroidChart
- **ML Kit**: QR Scanning and Face Detection

## Setup Instructions

### Prerequisites

- Android Studio Ladybug (or newer)
- Firebase Project



2. **Add Firebase**:
   - Create a project in the [Firebase Console](https://console.firebase.google.com/).
   - Add an Android app with the package name `com.example.university_attendence`.
   - Download the `google-services.json` file and place it in the `app/` directory.

3. **Enable Firebase Services**:
   - Enable **Email/Password** authentication.
   - Create a **Cloud Firestore** database.
   - Enable **Firebase Storage**.

4. **Build and Run**:
   - Open the project in Android Studio.
   - Sync Gradle.
   - Run the app on an emulator or physical device.

## Project Structure

- `data/`: Models and Repositories (Firebase implementation).
- `ui/`: Compose screens, components, and navigation.
- `di/`: Hilt dependency injection modules.
- `theme/`: Design system (Colors, Typography, Shapes).


