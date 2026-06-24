package com.example.university_attendence.ui.auth

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.university_attendence.ui.components.ModernTextField
import com.example.university_attendence.ui.theme.*

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundSlate
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // University Branding
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .shadow(8.dp, CircleShape),
                shape = CircleShape,
                color = Color.White
            ) {
                Icon(
                    Icons.Default.School,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.padding(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Global University",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Text(
                text = "Academic Portal Login",
                style = MaterialTheme.typography.bodyLarge,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(48.dp))

            ModernTextField(
                value = viewModel.email.value,
                onValueChange = viewModel::onEmailChange,
                label = "Email Address",
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = PrimaryBlue) }
            )

            ModernTextField(
                value = viewModel.password.value,
                onValueChange = viewModel::onPasswordChange,
                label = "Password",
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = PrimaryBlue) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            tint = TextGray
                        )
                    }
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(checkedColor = PrimaryBlue)
                    )
                    Text("Remember Me", style = MaterialTheme.typography.bodySmall, color = TextDark)
                }
                TextButton(onClick = { /* TODO */ }) {
                    Text("Forgot Password?", color = PrimaryBlue, style = MaterialTheme.typography.labelLarge)
                }
            }

            viewModel.error.value?.let { error ->
                Text(
                    text = error,
                    color = ErrorRed,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = { viewModel.login(onLoginSuccess) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                enabled = !viewModel.isLoading.value
            ) {
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Sign In to Portal", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                Text(" OR ", color = TextGray, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 16.dp))
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Icon(Icons.Default.Login, contentDescription = null, tint = TextDark)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Continue with Google", color = TextDark, fontWeight = FontWeight.SemiBold)
            }

            TextButton(
                onClick = onNavigateToRegister,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text("New Student? ", color = TextGray)
                Text("Create Portal Account", color = PrimaryBlue, fontWeight = FontWeight.Bold)
            }
        }
    }
}
