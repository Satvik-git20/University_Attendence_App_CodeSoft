package com.example.university_attendence.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.university_attendence.data.model.UserRole
import com.example.university_attendence.ui.components.ModernTextField
import com.example.university_attendence.ui.theme.*

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundSlate
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.displayLarge,
                color = PrimaryBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 48.dp)
            )
            
            Text(
                text = "Join our university community",
                style = MaterialTheme.typography.bodyLarge,
                color = TextGray,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            ModernTextField(
                value = viewModel.name.value,
                onValueChange = viewModel::onNameChange,
                label = "Full Name"
            )

            ModernTextField(
                value = viewModel.email.value,
                onValueChange = viewModel::onEmailChange,
                label = "Email Address"
            )

            ModernTextField(
                value = viewModel.password.value,
                onValueChange = viewModel::onPasswordChange,
                label = "Password",
                visualTransformation = PasswordVisualTransformation()
            )

            ModernTextField(
                value = viewModel.universityId.value,
                onValueChange = viewModel::onUniversityIdChange,
                label = "University / Faculty ID"
            )

            ModernTextField(
                value = viewModel.department.value,
                onValueChange = viewModel::onDepartmentChange,
                label = "Department"
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Role: ", style = MaterialTheme.typography.bodyLarge, color = TextDark, fontWeight = FontWeight.SemiBold)
                RadioButton(
                    selected = viewModel.role.value == UserRole.STUDENT,
                    onClick = { viewModel.onRoleChange(UserRole.STUDENT) },
                    colors = RadioButtonDefaults.colors(selectedColor = SecondaryEmerald)
                )
                Text("Student", color = TextDark)
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = viewModel.role.value == UserRole.INSTRUCTOR,
                    onClick = { viewModel.onRoleChange(UserRole.INSTRUCTOR) },
                    colors = RadioButtonDefaults.colors(selectedColor = SecondaryEmerald)
                )
                Text("Instructor", color = TextDark)
            }

            viewModel.error.value?.let { error ->
                Text(
                    text = error,
                    color = ErrorRed,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = { viewModel.register(onRegisterSuccess) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                enabled = !viewModel.isLoading.value
            ) {
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Sign Up", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier.padding(top = 16.dp, bottom = 48.dp)
            ) {
                Text("Already have an account? Sign In", color = PrimaryBlue, fontWeight = FontWeight.Bold)
            }
        }
    }
}
