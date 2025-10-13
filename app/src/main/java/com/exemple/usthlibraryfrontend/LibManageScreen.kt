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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exemple.usthlibraryfrontend.data.SessionManager
import com.exemple.usthlibraryfrontend.screen.AuthorScreen
import com.exemple.usthlibraryfrontend.screen.BookManager
import com.exemple.usthlibraryfrontend.screen.HomeScreen
import com.exemple.usthlibraryfrontend.screen.LoanScreen
import com.exemple.usthlibraryfrontend.screen.LoginScreen
import com.exemple.usthlibraryfrontend.screen.RegisterScreen
import com.exemple.usthlibraryfrontend.screen.ReviewScreen
import com.exemple.usthlibraryfrontend.screen.UserScreen

enum class LibManageScreen(val title: Int) {
    Home(title = R.string.home),
    User(title = R.string.user),
    Book(title = R.string.book),
    Author(title = R.string.author),
    Loan(title = R.string.loan),
    Review(title = R.string.review)
}

sealed class AuthScreen {
    object Login : AuthScreen()
    object Register : AuthScreen()
}

@Composable
fun LibManageBottomBar(onScreenSelected: (LibManageScreen) -> Unit = {}, modifier: Modifier = Modifier) {
    BottomAppBar {
        LibManageScreen.values().forEach { screen ->
            IconButton(
                onClick = { onScreenSelected(screen) },
                modifier = Modifier.weight(1f)
            ) {
                val iconRes = when (screen) {
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
    val context = LocalContext.current
    val sessionManager = remember  { SessionManager(context) }

    var isLoggedIn by remember { mutableStateOf(sessionManager.fetchAuthToken() != null) }
    var currentAuthScreen by remember { mutableStateOf<AuthScreen>(AuthScreen.Login) }

    // Đặt UserScreen làm màn hình mặc định sau khi đăng nhập
    var currentScreen by remember { mutableStateOf(LibManageScreen.User) }

    // Hàm xử lý đăng xuất (khi người dùng tự nhấn hoặc bị buộc)
    val handleLogout = {
        sessionManager.clearSession()
        isLoggedIn = false
        // Reset về màn hình login cho lần sau
        currentAuthScreen = AuthScreen.Login
    }

    if (isLoggedIn) {
        Scaffold(
            bottomBar = {
                LibManageBottomBar(onScreenSelected = { selected ->
                    currentScreen = selected
                })
            }
        ) { padding ->
            when (currentScreen) {
                LibManageScreen.Home -> HomeScreen(Modifier.padding(padding))
                LibManageScreen.User -> UserScreen(
                    modifier = Modifier.padding(padding),
                    onForceLogout = handleLogout // Truyền hàm đăng xuất
                )
                LibManageScreen.Book -> BookManager(Modifier.padding(padding))
                LibManageScreen.Author -> AuthorScreen(Modifier.padding(padding))
                LibManageScreen.Loan -> LoanScreen(Modifier.padding(padding))
                LibManageScreen.Review -> ReviewScreen(Modifier.padding(padding))
            }
        }
    } else {
        when (currentAuthScreen) {
            is AuthScreen.Login -> LoginScreen(
                onLoginSuccess = { isLoggedIn = true },
                onNavigateToRegister = { currentAuthScreen = AuthScreen.Register }
            )
            is AuthScreen.Register -> RegisterScreen(
                onRegisterSuccess = { currentAuthScreen = AuthScreen.Login },
                onNavigateToLogin = { currentAuthScreen = AuthScreen.Login }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun preview() {
    LibManageScreen()
}