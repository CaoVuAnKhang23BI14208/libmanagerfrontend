package com.example.usthlibraryfrontend.review

import com.exemple.usthlibraryfrontend.model.Reply
import com.exemple.usthlibraryfrontend.model.Review
import com.exemple.usthlibraryfrontend.model.Role
import com.exemple.usthlibraryfrontend.model.User
import java.util.*


object ReviewManager {
    private val reviews = mutableListOf<Review>()
    private var loggedInUser: User? = null
    private var replyCounter = 1
    private var reviewCounter = 1

    fun login(user: User) {
        loggedInUser = user
    }

    fun logout() {
        loggedInUser = null
    }

    fun getLoggedInUser(): User? = loggedInUser

    fun getReviewsByBook(bookId: Int): List<Review> {
        return reviews.filter { it.bookId == bookId }
    }

    fun addReview(bookId: Int, rating: Float, comment: String) {
        val user = loggedInUser ?: throw Exception("Please log in first.")
        val review = Review(
            id = reviewCounter++,
            userId = user.id,
            bookId = bookId,
            rating = rating,
            comment = comment,
            createdAt = Date()
        )
        reviews.add(review)
        println("✅ Review added by ${user.username} for bookId $bookId: $comment")
    }

    fun editReview(reviewId: Int, newComment: String, newRating: Float) {
        val user = loggedInUser ?: throw Exception("Login required.")
        val review = reviews.find { it.id == reviewId } ?: throw Exception("Review not found.")
        if (user.role == Role.ADMIN || review.userId == user.id) {
            review.comment = newComment
            review.rating = newRating
        } else throw Exception("Permission denied.")
    }

    fun deleteReview(reviewId: Int) {
        val user = loggedInUser ?: throw Exception("Login required.")
        val review = reviews.find { it.id == reviewId } ?: throw Exception("Review not found.")
        if (user.role == Role.ADMIN || review.userId == user.id) {
            reviews.remove(review)
        } else throw Exception("Permission denied.")
    }

    fun replyToReview(parentReviewId: Int, comment: String) {
        val user = loggedInUser ?: throw Exception("Login required.")
        val parent = reviews.find { it.id == parentReviewId } ?: throw Exception("Parent review not found.")

        // One level deep
        val reply = Reply(
            id = replyCounter++,
            userId = user.id,
            parentReviewId = parentReviewId,
            comment = "↳ Reply to ${parent.userId}: $comment",
            createdAt = Date()
        )
        parent.replies.add(reply)
        println("💬 Reply added by ${user.username}")
    }

    fun likeReview(reviewId: Int): Review {
        val user = loggedInUser ?: throw Exception("Login required.")
        val review = reviews.find { it.id == reviewId } ?: throw Exception("Review not found.")

        if (review.dislikes.contains(user.id)) {
            review.dislikes.remove(user.id)
        }

        if (review.likes.contains(user.id)) {
            review.likes.remove(user.id)
        } else {
            review.likes.add(user.id)
        }

        val updatedReview = review.copy(
            likes = review.likes.toMutableSet(),
            dislikes = review.dislikes.toMutableSet()
        )

        val index = reviews.indexOfFirst { it.id == review.id }
        if (index != -1) reviews[index] = updatedReview

        return updatedReview
    }


    fun dislikeReview(reviewId: Int): Review {
        val user = loggedInUser ?: throw Exception("Login required.")
        val review = reviews.find { it.id == reviewId } ?: throw Exception("Review not found.")

        if (review.likes.contains(user.id)) {
            review.likes.remove(user.id)
        }

        if (review.dislikes.contains(user.id)) {
            review.dislikes.remove(user.id)
        } else {
            review.dislikes.add(user.id)
        }

        val updatedReview = review.copy(
            likes = review.likes.toMutableSet(),
            dislikes = review.dislikes.toMutableSet()
        )

        val index = reviews.indexOfFirst { it.id == review.id }
        if (index != -1) reviews[index] = updatedReview

        return updatedReview
    }

}
