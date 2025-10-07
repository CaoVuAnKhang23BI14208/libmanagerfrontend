package com.exemple.usthlibraryfrontend.screen.loan

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exemple.usthlibraryfrontend.model.Loan
import com.exemple.usthlibraryfrontend.model.LoanRequest
import com.exemple.usthlibraryfrontend.model.loanRequest

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoanRequestsList(
    requestList: SnapshotStateList<LoanRequest>,
    activeLoans: SnapshotStateList<Loan>
) {
    RequestList(
        requestList = requestList,
        activeLoans = activeLoans,
        onRequestHandled = { request -> requestList.remove(request) }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RequestCard(
    request: LoanRequest,
    activeLoans: SnapshotStateList<Loan>,
    onRequestHandled: (LoanRequest) -> Unit
) {
    Card {
        Column {
            Text("Book title: ${request.bookTitle}")
            Text("User ID: ${request.userId}")
            Text("Request Date: ${request.requestDate}")
            Text("Due Date: ${request.returnDate}")

            OutlinedButton(
                onClick = {
                    val nextId = (activeLoans.maxOfOrNull { it.id } ?: 0) + 1
                    activeLoans.add(
                        Loan(
                            id = nextId,
                            bookTitle = request.bookTitle,
                            userId = request.userId,
                            loanDate = request.requestDate,
                            dueDate = request.returnDate,
                            returnDate = null
                        )
                    )
                    onRequestHandled(request)
                },
                modifier = Modifier.defaultMinSize(200.dp)
            ) {
                Text("Approved")
            }

            OutlinedButton(
                onClick = { onRequestHandled(request) },
                modifier = Modifier.defaultMinSize(200.dp)
            ) {
                Text("Reject")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RequestList(
    requestList: SnapshotStateList<LoanRequest>,
    activeLoans: SnapshotStateList<Loan>,
    onRequestHandled: (LoanRequest) -> Unit
) {
    LazyColumn {
        items(requestList) {
            RequestCard(
                request = it,
                activeLoans = activeLoans,
                onRequestHandled = onRequestHandled
            )
        }
    }
}
