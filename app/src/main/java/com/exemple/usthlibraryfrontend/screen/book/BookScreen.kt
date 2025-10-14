package com.exemple.usthlibraryfrontend.screen.book

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exemple.usthlibraryfrontend.R
import com.exemple.usthlibraryfrontend.model.Book
import com.exemple.usthlibraryfrontend.model.books
import com.exemple.usthlibraryfrontend.viewmodel.BookViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun BookManager(
    modifier: Modifier = Modifier,
    bookViewModel: BookViewModel = viewModel()
) {
    val bookUiState by bookViewModel.uiState.collectAsState()

    when (bookUiState.currentScreen) {
        "list" -> BookScreen(bookViewModel = bookViewModel)
        "add" -> BookUpload(bookViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookScreen(
    modifier: Modifier = Modifier,
    bookViewModel: BookViewModel
) {
    val bookUiState by bookViewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        )
    {
        Text(
            text = stringResource(R.string.searchForBooks)
        )

        OutlinedTextField(
            value = bookUiState.search,
            onValueChange = {bookViewModel.updateSearch(it)},
            label = {Text("Search")}
        )

        Button(
            onClick = { bookViewModel.switchScreen("add")},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text(text = "Upload")
        }

        bookList(bookUiState.books)
    }
}

@Composable
fun bookCard(book: Book, modifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(book.coverImageRes),
                contentDescription = null,
                alignment = Alignment.CenterStart,
                modifier = Modifier
                    .defaultMinSize(60.dp, 90.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                Text(
                    text = book.title
                )
                Text(
                    text = "Written by " + book.author
                )
                Text(
                    text = "Published at " + book.publishedDate
                )
            }
            when (book.isLoan) {
                true -> Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.width(100.dp)

                ) {
                    Text(
                        text = stringResource(R.string.loaned)
                    )
                }
                false -> Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue
                    ),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text(
                        text = stringResource(R.string.active)
                    )
                }
            }
        }
    }


@Composable
fun bookList(bookList: List<Book>) {
    LazyColumn {
        items(bookList) {
            bookCard(book = it)
            Divider(
                color = Color.Gray.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
