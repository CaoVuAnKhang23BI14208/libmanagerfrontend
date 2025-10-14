package com.exemple.usthlibraryfrontend.screen.loan

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exemple.usthlibraryfrontend.model.Loan
import com.exemple.usthlibraryfrontend.model.LoanRequest
import com.exemple.usthlibraryfrontend.model.loanRequest
import com.exemple.usthlibraryfrontend.viewmodel.LoanViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoanRequestsList(
    loanViewModel: LoanViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val loanUiState by loanViewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(loanUiState.loanRequests) {
            RequestCard(
                request = it,
                onApprove = { loanViewModel.approveLoanRequest(it )},
                onReject = { loanViewModel.rejectLoanRequest(it)}
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RequestCard(
    request: LoanRequest,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Card {
        Column {
            Text("Book title: ${request.bookTitle}")
            Text("User ID: ${request.userId}")
            Text("Request Date: ${request.requestDate}")
            Text("Due Date: ${request.returnDate}")

            OutlinedButton(
                onClick = onApprove,
                modifier = Modifier.defaultMinSize(200.dp)
            ) {
                Text("Approved")
            }

            OutlinedButton(
                onClick = onReject,
                modifier = Modifier.defaultMinSize(200.dp)
            ) {
                Text("Reject")
            }
        }
    }
}


