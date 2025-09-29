package com.exemple.usthlibraryfrontend.model

import android.service.autofill.DateTransformation
import java.util.Date

data class Author(
    val id: Int,
    val name: String,
    val birthDate: Date,
    val bio: String
)

