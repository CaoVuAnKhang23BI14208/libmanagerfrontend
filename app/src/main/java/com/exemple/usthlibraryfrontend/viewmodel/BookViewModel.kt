package com.exemple.usthlibraryfrontend.viewmodel

import android.icu.text.CaseMap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exemple.usthlibraryfrontend.R
import com.exemple.usthlibraryfrontend.model.Book
import com.exemple.usthlibraryfrontend.model.books
import com.exemple.usthlibraryfrontend.screen.book.bookList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class BookUiState(
    val books: List<Book> = emptyList(),
    val search: String = "",
    val title: String = "",
    val author: String = "",
    val publishedDate: String = "",
    val isbn: String = "",
    val category: String = "",
    val currentScreen: String = ""
)

@RequiresApi(Build.VERSION_CODES.O)
class BookViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState

    private val bookList = mutableListOf<Book>()

    init {
        loadInitData()
    }

    private fun loadInitData() {
        viewModelScope.launch {
            bookList.addAll(books)
            _uiState.update {
                it.copy(
                    books = bookList,
                    currentScreen = "list"
                )
            }
        }
    }

    fun updateSearch(search: String) {
        _uiState.update {
            it.copy(
                search = search,
                books = bookList.filter {
                    search.isEmpty() ||
                            it.id.toString() == search ||
                            it.title.contains(search, ignoreCase = true) ||
                            it.author.contains(search, ignoreCase = true) ||
                            it.publishedDate.toString().contains(search)
                }
            )
        }
    }

    fun addBook() {
        viewModelScope.launch {
            val state = _uiState.value
            val newBook = Book(
                id = (state.books.maxOfOrNull { it.id } ?: 0) + 1,
                title = state.title,
                author = state.author,
                publishedDate = state.publishedDate,
                isbn = state.isbn,
                category = state.category,
                coverImageRes = R.drawable.img3
            )
            bookList.add(newBook)
            _uiState.update {
                it.copy(
                    books = bookList.toList(),
                    title = "",
                    author = "",
                    publishedDate = "",
                    isbn = "",
                    category = "",
                    search = "",
                    currentScreen = "list"
                )
            }
        }
    }

    fun cancelAdd() {
        _uiState.update {
            it.copy(
                title = "",
                author = "",
                publishedDate = "",
                isbn = "",
                category = "",
                search = "",
                currentScreen = "list"
            )
        }
    }

    fun updateField(field: String, value: String) {
        _uiState.update {
            when (field) {
                "title" -> it.copy(title = value)
                "author" -> it.copy(author = value)
                "publishedDate" -> it.copy(publishedDate = value)
                "isbn" -> it.copy(isbn = value)
                "category" -> it.copy(category = value)
                else -> it
            }
        }
    }

    fun switchScreen(screen: String) {
        _uiState.update {
            it.copy(currentScreen = screen)
        }
    }
}