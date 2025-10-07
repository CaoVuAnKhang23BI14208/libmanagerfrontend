package com.exemple.usthlibraryfrontend.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exemple.usthlibraryfrontend.R
import com.exemple.usthlibraryfrontend.model.*
import com.example.usthlibraryfrontend.review.ReviewManager
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BookReviewScreen(
    book: Book,
    reviewManager: ReviewManager,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentUser by remember { mutableStateOf<User?>(null) }

    // Auto login for testing
    LaunchedEffect(Unit) {
        reviewManager.login(
            User(
                id = 1,
                username = "Linh",
                role = Role.ADMIN,
                email = "william.henry.moody@my-own-personal-domain.com",
                password = "123",
                profilePictureRes = R.drawable.avatar
            )
        )
        currentUser = reviewManager.getLoggedInUser()
    }

    val reviews = remember { mutableStateListOf<Review>() }

    // Load reviews
    LaunchedEffect(Unit) {
        reviews.clear()
        reviews.addAll(reviewManager.getReviewsByBook(book.id))
    }

    var commentText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 280.dp)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = book.coverImageRes),
                contentDescription = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.height(8.dp))
            Text(book.title, style = MaterialTheme.typography.titleLarge)
            Text("By ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text("Category: ${book.category}", style = MaterialTheme.typography.bodySmall)

            Spacer(Modifier.height(8.dp))
            Text("Reviews", style = MaterialTheme.typography.titleMedium)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .weight(1f, false)
            ) {
                items(reviews) { review ->
                    ReviewCard(
                        review = review,
                        reviewManager = reviewManager,
                        currentUser = currentUser,
                        onRefresh = {
                            reviews.clear()
                            reviews.addAll(reviewManager.getReviewsByBook(book.id))
                        }
                    )
                }
            }
        }

        // Bottom review input box
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .navigationBarsPadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                item {
                    Button(
                        onClick = {
                            if (currentUser != null && rating in 0.1f..5f && commentText.isNotBlank()) {
                                reviewManager.addReview(book.id, rating, commentText)
                                commentText = ""
                                rating = 0f
                                reviews.clear()
                                reviews.addAll(reviewManager.getReviewsByBook(book.id))
                            }
                        },
                        enabled = currentUser != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp)
                    ) {
                        Text(
                            if (currentUser != null)
                                "Post Review"
                            else
                                "Log in to Post Review"
                        )
                    }

                    Divider(Modifier.padding(vertical = 2.dp))

                    Text("Add a Rating", fontWeight = FontWeight.Bold)

                    Slider(
                        value = rating,
                        onValueChange = { rating = it },
                        valueRange = 0f..5f // smooth slider
                    )

                    Text("Your rating: ${"%.1f".format(rating)} ⭐")

                    Spacer(Modifier.height(2.dp))

                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        label = { Text("Write your review...") },
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun ReviewCard(
    review: Review,
    reviewManager: ReviewManager,
    currentUser: User?,
    onRefresh: () -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedComment by remember { mutableStateOf(review.comment) }
    var replyText by remember { mutableStateOf("") }

    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(text = "User #${review.userId}", fontWeight = FontWeight.Bold)
                    Text(sdf.format(review.createdAt), style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(Modifier.height(8.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = editedComment,
                    onValueChange = { editedComment = it },
                    label = { Text("Edit your review") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
                    reviewManager.editReview(review.id, editedComment, review.rating)
                    isEditing = false
                    onRefresh()
                }) {
                    Text("Save")
                }
            } else {
                Text("⭐ ${"%.1f".format(review.rating)}/5", fontWeight = FontWeight.Bold)
                Text(review.comment)
            }

            Spacer(Modifier.height(8.dp))

            // Like / Dislike / Edit / Delete
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    reviewManager.likeReview(review.id)
                    onRefresh()
                }) {
                    Text("👍 ${review.likes.size}")
                }

                Button(onClick = {
                    reviewManager.dislikeReview(review.id)
                    onRefresh()
                }) {
                    Text("👎 ${review.dislikes.size}")
                }


                if (currentUser?.id == review.userId) {
                    Button(onClick = { isEditing = !isEditing }) { Text("✏️ ") }
                    Button(onClick = {
                        reviewManager.deleteReview(review.id)
                        onRefresh()
                    }) { Text("🗑") }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Replies
            if (review.replies.isNotEmpty()) {
                Column(Modifier.padding(start = 16.dp)) {
                    Text("Replies:", fontWeight = FontWeight.Bold)
                    review.replies.forEach { reply ->
                        Text("↳ ${reply.comment}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            // Add reply
            OutlinedTextField(
                value = replyText,
                onValueChange = { replyText = it },
                label = { Text("Reply to this review...") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (replyText.isNotBlank()) {
                        reviewManager.replyToReview(review.id, replyText)
                        replyText = ""
                        onRefresh()
                    }
                },
                enabled = currentUser != null,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (currentUser != null) "Post Reply" else "Log in to Reply")
            }
        }
    }
}
