package com.exemple.usthlibraryfrontend.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exemple.usthlibraryfrontend.R

val list = listOf<Int>(
    R.drawable.img1,
    R.drawable.img2,
    R.drawable.img3,
    R.drawable.img4,
    R.drawable.img5,
    R.drawable.img6
)

@Composable
fun lazyrow(list: List<Int>, name: Int ,modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(name),
            textAlign = TextAlign.Start
        )
        LazyRow {
            items(list) {
                Image(
                    painter = painterResource(it),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}
@Preview
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        Text(
            text = stringResource(R.string.home)
        )

        lazyrow(list, R.string.justArrived)

        lazyrow(list, R.string.topBorrowedBooks)

        lazyrow(list, R.string.popular)

        lazyrow(list, R.string.recommend)
    }
}