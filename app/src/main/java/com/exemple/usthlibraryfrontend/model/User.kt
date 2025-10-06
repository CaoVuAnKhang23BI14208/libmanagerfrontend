package com.exemple.usthlibraryfrontend.model

enum class Role() {
    ADMIN,
    MEMBER
}

data class User(
    val id: String,
    val username: String,
    val password: String,
    val email: String,
    val role: Role,
    val profilePictureRes: Int
)

val users = listOf<User>(
    User("a", "sd", "asd", "asd", Role.MEMBER, 1),
    User("b", "sd", "asd", "asd", Role.ADMIN, 1),
    User("c", "sd", "asd", "asd", Role.MEMBER, 1),
    User("d", "sd", "asd", "asd", Role.ADMIN, 1),
    User("e", "sd", "asd", "asd", Role.ADMIN, 1),
    User("f", "sd", "asd", "asd", Role.ADMIN, 1),
    User("g", "sd", "asd", "asd", Role.MEMBER, 1)
)
