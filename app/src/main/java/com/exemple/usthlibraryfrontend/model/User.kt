package com.exemple.usthlibraryfrontend.model

enum class Role() {
    ADMIN,
    MEMBER
}

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val role: Role,
    val profilePictureRes: Int
)

val users = listOf<User>(
    User(1, "sd", "asd", "asd", Role.MEMBER, 1),
    User(2, "sd", "asd", "asd", Role.ADMIN, 1),
    User(3, "sd", "asd", "asd", Role.MEMBER, 1),
    User(4, "sd", "asd", "asd", Role.ADMIN, 1),
    User(5, "sd", "asd", "asd", Role.ADMIN, 1),
    User(6, "sd", "asd", "asd", Role.ADMIN, 1),
    User(7, "sd", "asd", "asd", Role.MEMBER, 1)
)
