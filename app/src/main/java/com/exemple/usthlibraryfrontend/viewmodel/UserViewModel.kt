package com.exemple.usthlibraryfrontend.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.exemple.usthlibraryfrontend.data.SessionManager
import com.exemple.usthlibraryfrontend.model.Role
import com.exemple.usthlibraryfrontend.model.User
import com.exemple.usthlibraryfrontend.remote.UserService
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val sessionManager = SessionManager(application)
    private val userService = UserService(application)

    val userName = MutableStateFlow(sessionManager.fetchUserName() ?: "Guest")
    val userEmail = MutableStateFlow(sessionManager.fetchUserEmail() ?: "guest@example.com")

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList = _userList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _forceLogout = MutableSharedFlow<Unit>()
    val forceLogout = _forceLogout.asSharedFlow()

    fun logout() {
        sessionManager.clearSession()
    }

    fun fetchAllUsers(searchQuery: String, role: Role?) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = userService.getAllUsers(fullname = searchQuery.ifBlank { null })
                _userList.value = response.users
            } catch (e: Exception) {

                if (e is ClientRequestException && e.response.status.value == 401) {
                    _errorMessage.value = "Session expired. Please log in again."
                    _forceLogout.emit(Unit)
                } else {
                    _errorMessage.value = "Error fetching users: ${e.message}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}