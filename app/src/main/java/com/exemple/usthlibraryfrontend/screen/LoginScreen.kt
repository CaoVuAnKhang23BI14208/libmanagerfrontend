package com.exemple.usthlibraryfrontend.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.exemple.usthlibraryfrontend.data.SessionManager
import com.exemple.usthlibraryfrontend.remote.LoginRequest
import com.exemple.usthlibraryfrontend.remote.UserService
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val userService = remember { UserService(context) }
    val sessionManager = remember { SessionManager(context) }
    Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
                text = "Login",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            errorMessage?.let { Text(text = it, color = Color.Red) }
            Button(
                    onClick = {
                        isLoading = true
                        errorMessage = null
                        coroutineScope.launch {
                            try {
                                val loginRequest = LoginRequest(email, password)
                                val response = userService.login(loginRequest)
                                if (response.token != null && response.user != null) {
                                    sessionManager.saveUserSession(response.token, response.user)
                                    onLoginSuccess()
                                } else {
                                    errorMessage = response.message
                                }
                            } catch (e: Exception) {
                                errorMessage = e.message ?: "An unknown error occurred"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
            ) { Text("Login") }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNavigateToRegister, modifier = Modifier.fillMaxWidth()) {
            Text("Don't have an account? Register")
        }
    }
}
