package com.exemple.usthlibraryfrontend.screen.loan

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import com.exemple.usthlibraryfrontend.model.Loan
import com.exemple.usthlibraryfrontend.model.LoanRequest
import com.exemple.usthlibraryfrontend.model.loanRequest

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoanScreen(modifier: Modifier = Modifier) {
    val layoutDirection = LocalLayoutDirection.current
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val requestList = rememberSaveable { mutableStateListOf<LoanRequest>().apply { addAll(loanRequest) } }
    val activeLoan = remember { mutableStateListOf<Loan>() }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(
            start = WindowInsets.safeDrawing.asPaddingValues()
                .calculateStartPadding(layoutDirection),
            end = WindowInsets.safeDrawing.asPaddingValues()
                .calculateEndPadding(layoutDirection)
        )) {
        Column {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    text = { Text("Active Loans") },
                    selected = selectedTab == 0,
                    onClick = {selectedTab = 0}
                )
                Tab(
                    text = { Text("Loan Requests") },
                    selected = selectedTab == 1,
                    onClick = {selectedTab = 1}
                )
            }

            when (selectedTab) {
                0 -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ActiveLoansList()
                }

                1 -> LoanRequestsList(requestList, activeLoan)
            }
        }
    }
}