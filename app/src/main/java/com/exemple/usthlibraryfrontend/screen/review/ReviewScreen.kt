package com.exemple.usthlibraryfrontend.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.usthlibraryfrontend.review.ReviewManager
import com.exemple.usthlibraryfrontend.model.Book
import com.exemple.usthlibraryfrontend.model.books

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun ReviewManagerScreen(modifier: Modifier = Modifier) {
    var currentScreen by rememberSaveable { mutableStateOf("bookList") }
    var selectedBook by remember { mutableStateOf<Book?>(null) }

    val reviewManager = ReviewManager

    when (currentScreen) {
        "bookList" -> ReviewBookListScreen(
            onBookClick = {
                selectedBook = it
                currentScreen = "reviews"
            },
            modifier = modifier
        )

        "reviews" -> selectedBook?.let { book ->
            BookReviewScreen(
                book = book,
                reviewManager = reviewManager,
                onBackClick = { currentScreen = "bookList" },
                modifier = modifier
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewBookListScreen(modifier: Modifier = Modifier, onBookClick: (Book) -> Unit) {
    var search by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📚 Select a Book to View Reviews",
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Search books") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        val filteredBooks = books.filter {
            search.isEmpty() ||
                    it.title.contains(search, ignoreCase = true) ||
                    it.author.contains(search, ignoreCase = true) ||
                    it.category.contains(search, ignoreCase = true)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredBooks) { book ->
                ReviewBookCard(book, onBookClick)
            }
        }
    }
}

@Composable
fun ReviewBookCard(book: Book, onBookClick: (Book) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .defaultMinSize(minHeight = 100.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F4F4))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(book.coverImageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .padding(end = 12.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = book.title, fontWeight = FontWeight.Bold)
                Text(text = "by ${book.author}")
                Text(text = "Category: ${book.category}")
            }

            Button(
                onClick = { onBookClick(book) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text("View Reviews")
            }
        }
    }
}
