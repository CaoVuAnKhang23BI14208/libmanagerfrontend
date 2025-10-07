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
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BookUpload(onUpload: (Book) -> Unit = {}, onCancel: () -> Unit = {}, modifier: Modifier = Modifier) {
    var id by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var author by rememberSaveable { mutableStateOf("") }
    var publishedDate by rememberSaveable { mutableStateOf("") }
    var isbn by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "Book details"
            )
            
            rowDetails(
                text = "ID:",
                value = id,
                onValueChange = { id = it }
            )

            rowDetails(
                text = "Title:",
                value = title,
                onValueChange = { title = it }
            )

            rowDetails(
                text = "Author:",
                value = author,
                onValueChange = { author = it}
            )

            rowDetails(
                text = "Published Date:",
                value = publishedDate,
                onValueChange = { publishedDate = it }
            )

            rowDetails(
                text = "ISBN:",
                value = isbn,
                onValueChange = { isbn = it }
            )

            rowDetails(
                text = "Category:",
                value = category,
                onValueChange = { category = it }
            )

            Row {
                Button(onClick = {
                    val newBook = Book(
                        id = id.toInt(),
                        title = title,
                        author = author,
                        publishedDate = LocalDate.parse(publishedDate),
                        isbn = isbn,
                        category = category,
                        coverImageRes = R.drawable.img1
                    )
                    books.add(newBook)
                    onUpload(newBook)
                }) {
                    Text("Save")
                }

                Button(onClick = { onCancel() }) {
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

