package com.exemple.usthlibraryfrontend.model

import java.util.Date

data class Loan(
    val id: Int,
    val bookId: Int,
    val userId: Int,
    val loanDate: Date,
    val dueDate: Date,
    val returnDate: Date
)

fun isReturned() {

}