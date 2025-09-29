package com.exemple.usthlibraryfrontend.model

import org.w3c.dom.Comment
import java.util.Date

data class Review(
    val id: Int,
    val userId: Int,
    val bookId: Int,
    val rating: Float,
    val comment: String,
    val createdAt: Date
)