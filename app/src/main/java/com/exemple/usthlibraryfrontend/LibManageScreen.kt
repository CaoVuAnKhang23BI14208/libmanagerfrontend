package com.exemple.usthlibraryfrontend

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exemple.usthlibraryfrontend.screen.AuthorScreen
import com.exemple.usthlibraryfrontend.screen.book.BookManager
import com.exemple.usthlibraryfrontend.screen.HomeScreen
import com.exemple.usthlibraryfrontend.screen.ReviewManagerScreen
import com.exemple.usthlibraryfrontend.screen.loan.LoanScreen
import com.exemple.usthlibraryfrontend.screen.UserScreen

enum class LibManageScreen(val title: Int) {
    Home(title = R.string.home),
    User(title = R.string.user),
    Book(title = R.string.book),
    Author(title = R.string.author),
    Loan(title = R.string.loan),
    Review(title = R.string.review)
}

@Composable
fun LibManageBottomBar(onScreenSelected: (LibManageScreen) -> Unit = {}, modifier: Modifier = Modifier) {
    BottomAppBar {
        LibManageScreen.values().forEach {
            screen -> IconButton(
                onClick = {onScreenSelected(screen)},
                modifier = Modifier.weight(1f)
            ) {
                val iconRes = when(screen) {
                    LibManageScreen.Home -> R.drawable.home_24dp_e3e3e3_fill0_wght400_grad0_opsz24
                    LibManageScreen.User -> R.drawable.user_attributes_24dp_e3e3e3_fill0_wght400_grad0_opsz24
                    LibManageScreen.Book -> R.drawable.shelves_24dp_e3e3e3_fill0_wght400_grad0_opsz24
                    LibManageScreen.Author -> R.drawable.person_book_24dp_e3e3e3_fill0_wght400_grad0_opsz24
                    LibManageScreen.Loan -> R.drawable.money_bag_24dp_e3e3e3_fill0_wght400_grad0_opsz24
                    LibManageScreen.Review -> R.drawable.reviews_24dp_e3e3e3_fill0_wght400_grad0_opsz24
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(iconRes),
                        modifier = Modifier.size(20.dp),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(screen.title)
                    )
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LibManageScreen() {
    var currentScreen by rememberSaveable { mutableStateOf(LibManageScreen.Home) }

    Scaffold(
        bottomBar = {
            LibManageBottomBar( {
                selected -> currentScreen = selected
            })
        }
    ) { padding ->
        when (currentScreen) {
            LibManageScreen.Home -> HomeScreen(Modifier.padding(padding))
            LibManageScreen.User -> UserScreen(Modifier.padding(padding))
            LibManageScreen.Book -> BookManager(Modifier.padding(padding))
            LibManageScreen.Author -> AuthorScreen()
            LibManageScreen.Loan -> LoanScreen()
            LibManageScreen.Review -> ReviewManagerScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun preview() {
    LibManageScreen()
}