package com.exemple.usthlibraryfrontend.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class Role() {
    ADMIN,
    MEMBER
}
@Serializable
data class User(
    @SerialName("_id")
    val id: String,
    val email: String,
    val profilePicture: String,
    val fullname: String,
)
