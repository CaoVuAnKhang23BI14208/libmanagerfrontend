package com.exemple.usthlibraryfrontend.screen.book

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.exemple.usthlibraryfrontend.R
import com.exemple.usthlibraryfrontend.model.Book
import com.exemple.usthlibraryfrontend.model.books
import com.exemple.usthlibraryfrontend.viewmodel.BookViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookUpload(bookViewModel: BookViewModel, modifier: Modifier = Modifier) {
    val bookUiState by bookViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "Book details"
            )
            rowDetails(
                text = "Title:",
                value = bookUiState.title,
                onValueChange = { bookViewModel.updateField("title", it)}
            )

            rowDetails(
                text = "Author:",
                value = bookUiState.author,
                onValueChange = { bookViewModel.updateField("author", it)}
            )

            rowDetails(
                text = "Published Date:",
                value = bookUiState.publishedDate,
                onValueChange = { bookViewModel.updateField("publishedDate", it) }
            )

            rowDetails(
                text = "ISBN:",
                value = bookUiState.isbn,
                onValueChange = { bookViewModel.updateField("isbn", it) }
            )

            rowDetails(
                text = "Category:",
                value = bookUiState.category,
                onValueChange = { bookViewModel.updateField("category", it) }
            )

            Row {
                Button(onClick = {
                    bookViewModel.addBook()
                }) {
                    Text("Save")
                }

                Button(onClick = {
                    bookViewModel.cancelAdd()
                }) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
fun rowDetails(text: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text
        )
        Spacer(modifier = modifier.weight(1f))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange
        )
    }
}

