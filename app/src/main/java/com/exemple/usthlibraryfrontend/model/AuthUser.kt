package com.exemple.usthlibraryfrontend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class AuthUser(
    val id: String,
    val fullname: String,
    val email: String,
)