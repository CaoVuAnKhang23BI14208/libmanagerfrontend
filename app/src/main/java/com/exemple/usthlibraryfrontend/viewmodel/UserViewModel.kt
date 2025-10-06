
package com.exemple.usthlibraryfrontend.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.exemple.usthlibraryfrontend.data.SessionManager

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val sessionManager = SessionManager(application)

    val userName = mutableStateOf("")
    val userEmail = mutableStateOf("")

    init {
        loadUserData()
    }

    private fun loadUserData() {
        userName.value = sessionManager.fetchUserName() ?: "Guest"
        userEmail.value = sessionManager.fetchUserEmail() ?: "guest@example.com"
    }

    fun logout() {
        sessionManager.clearSession()
    }
}