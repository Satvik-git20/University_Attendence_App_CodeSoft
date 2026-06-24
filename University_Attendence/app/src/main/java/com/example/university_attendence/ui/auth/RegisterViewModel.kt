package com.example.university_attendence.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.university_attendence.data.model.User
import com.example.university_attendence.data.model.UserRole
import com.example.university_attendence.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _role = mutableStateOf(UserRole.STUDENT)
    val role: State<UserRole> = _role

    private val _department = mutableStateOf("")
    val department: State<String> = _department

    private val _universityId = mutableStateOf("")
    val universityId: State<String> = _universityId

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun onNameChange(value: String) { _name.value = value }
    fun onEmailChange(value: String) { _email.value = value }
    fun onPasswordChange(value: String) { _password.value = value }
    fun onRoleChange(value: UserRole) { _role.value = value }
    fun onDepartmentChange(value: String) { _department.value = value }
    fun onUniversityIdChange(value: String) { _universityId.value = value }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val user = User(
                name = _name.value,
                email = _email.value,
                role = _role.value,
                department = _department.value,
                universityId = _universityId.value,
                facultyId = if (_role.value == UserRole.INSTRUCTOR) _universityId.value else null,
                rollNumber = if (_role.value == UserRole.STUDENT) _universityId.value else null
            )
            val result = authRepository.register(user, _password.value)
            _isLoading.value = false
            if (result.isSuccess) {
                onSuccess()
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Registration failed"
            }
        }
    }
}
