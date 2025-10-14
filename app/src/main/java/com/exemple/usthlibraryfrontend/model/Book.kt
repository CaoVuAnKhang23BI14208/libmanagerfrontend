package com.exemple.usthlibraryfrontend.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import com.exemple.usthlibraryfrontend.R
import java.time.LocalDate

data class Book(
    val id: Int,
    val title: String,
    // phai sua lai thanh Author
    val author: String,
    val isbn: String,
    val publishedDate: String,
    val coverImageRes: Int,
    val category: String,
    // phải sửa type thành Review
    val reviews: String? = null,
    val isLoan: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
val books = mutableStateListOf<Book>(
    Book(1, "gddsa", "qf", "qwd","2020-12-12", R.drawable.img1, "dáq", "fewff", true),
    Book(2, "gddsa", "qf","qwd", "2020-12-12", R.drawable.img2, "dáq", "fewff", true),
    Book(3, "gddsa", "qf","qwd", "2020-12-12", R.drawable.img3, "dáq", "fewff", false),
    Book(4, "gddsa", "qf","qwd", "2020-12-12", R.drawable.img4, "dáq", "fewff", false),
    Book(5, "gddsa", "qf","qwd", "2020-12-12", R.drawable.img5, "dáq", "fewff", true),
    Book(6, "gddsa", "qf","qwd", "2020-12-12", R.drawable.img6, "dáq", "fewff", false),
    Book(7, "gddsa", "qf","qwd", "2020-12-12", R.drawable.img3, "dáq", "fewff", true),
    Book(8, "gddsa", "qf","qwd", "2020-12-12", R.drawable.img1, "dáq", "fewff", false),
    Book(9, "gddsa", "qf","qwd", "2020-12-12", R.drawable.img5, "dáq", "fewff", true),
    Book(10, "gddsa", "qf", "qwd","2020-12-12", R.drawable.img2, "dáq", "fewff", true)
)
