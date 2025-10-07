package com.exemple.usthlibraryfrontend.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.exemple.usthlibraryfrontend.screen.loan.LoanScreen
import java.time.LocalDate
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
data class Loan(
    val id: Int,
    val bookTitle: String,
    val userId: Int,
    val loanDate: LocalDate,
    val dueDate: LocalDate,
    val returnDate: LocalDate?
) {
    val isExpired: Boolean
        get() {
            return (returnDate == null) && LocalDate.now().isAfter(dueDate)
        }
}

data class LoanRequest(
    val bookTitle: String,
    val userId: Int,
    val requestDate: LocalDate,
    val returnDate: LocalDate
)

@RequiresApi(Build.VERSION_CODES.O)
val loanRequest = mutableListOf<LoanRequest>(
    LoanRequest(
        "ASdd",
        10,
        LocalDate.of(2025, 6, 7),
        returnDate = LocalDate.of(2025, 7, 1)
    ),
    LoanRequest(
        "caddc",
        10,
        LocalDate.of(2025, 6, 7),
        returnDate = LocalDate.of(2025, 7, 1)
    ),
    LoanRequest(
        "ASdqwed",
        10,
        LocalDate.of(2025, 6, 7),
        returnDate = LocalDate.of(2025, 7, 1)
    ),
    LoanRequest(
        "AqweSdd",
        10,
        LocalDate.of(2025, 6, 7),
        returnDate = LocalDate.of(2025, 7, 1)
    )
)
@RequiresApi(Build.VERSION_CODES.O)
val loans = mutableListOf<Loan>(
    Loan(
        1,
        "asdsa",
        1,
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2025, 12, 12),
        returnDate = null
    ),
    Loan(
        2,
        "dsv",
        2,
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2025, 2, 12),
        returnDate = LocalDate.of(2025, 1, 12)
    ),Loan(
        3,
        "aasfa",
        3,
        LocalDate.of(2024, 12, 1),
        LocalDate.of(2020, 12, 12),
        returnDate = LocalDate.of(2024, 12, 12)
    ),Loan(
        4,
        "asdaasa",
        4,
        LocalDate.of(2024, 12, 1),
        LocalDate.of(2024, 12, 12),
        returnDate = null
    ),
)
