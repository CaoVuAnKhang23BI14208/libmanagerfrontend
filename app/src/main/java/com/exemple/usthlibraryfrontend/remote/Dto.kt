package com.exemple.usthlibraryfrontend.remote

import com.exemple.usthlibraryfrontend.model.AuthUser
import com.exemple.usthlibraryfrontend.model.User
import kotlinx.serialization.Serializable

// DTO cho Login/Register
@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class LoginResponse(val message: String, val token: String? = null, val user: AuthUser? = null)

@Serializable
data class RegisterRequest(val fullname: String, val email: String, val password: String)

@Serializable
data class RegisterResponse(val message: String, val user: AuthUser? = null)

// DTO cho GetAllUsers
@Serializable
data class GetAllUsersResponse(
    val message: String,
    val total: Int,
    val currentPage: Int,
    val totalPages: Int,
    val users: List<User>
)