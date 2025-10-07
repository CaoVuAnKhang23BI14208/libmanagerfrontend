package com.exemple.usthlibraryfrontend.model

import org.w3c.dom.Comment
import java.util.Date

data class Review(
    val id: Int,
    val userId: Int,
    val bookId: Int,
    var rating: Float,
    var comment: String,
    val replies: MutableList<Reply> = mutableListOf(), // <-- add this line
    val createdAt: Date = Date(),
    val parentReviewId: Int? = null,
    val likes: MutableSet<Int> = mutableSetOf(),
    val dislikes: MutableSet<Int> = mutableSetOf()
)
data class Reply(
    val id: Int,
    val userId: Int,
    val parentReviewId: Int,
    var comment: String,
    val createdAt: Date
)