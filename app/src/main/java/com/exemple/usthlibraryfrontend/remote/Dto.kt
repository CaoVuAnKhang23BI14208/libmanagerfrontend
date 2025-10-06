package com.exemple.usthlibraryfrontend.remote

import com.exemple.usthlibraryfrontend.model.AuthUser
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class LoginResponse(val message: String, val token: String? = null, val user: AuthUser? = null)

@Serializable
data class RegisterRequest(val fullname: String, val email: String, val password: String)

@Serializable
data class RegisterResponse(val message: String, val user: AuthUser? = null)
