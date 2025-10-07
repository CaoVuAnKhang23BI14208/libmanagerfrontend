package com.exemple.usthlibraryfrontend.screen.loan

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exemple.usthlibraryfrontend.model.Loan
import com.exemple.usthlibraryfrontend.model.loans
import kotlin.math.exp

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ActiveLoansList(modifier: Modifier = Modifier) {
    var search by rememberSaveable { mutableStateOf("") }
    var selectedFilter by rememberSaveable { mutableStateOf<String?>(null) }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        dropDownMenu(onFilterSelected = {selectedFilter = it})

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = search,
            onValueChange = {search = it},
            label = {Text("Search")},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        val filteredLoan = loans.filter { it ->
            (when(selectedFilter) {
                "All" -> true
                "Active" -> !it.isExpired
                "Expired" -> it.isExpired
                else -> true
            })
                    && (search.isEmpty() ||
                            it.id.toString() == search ||
                            it.bookTitle.contains(search, ignoreCase = true) ||
                            it.userId.toString() == search ||
                            it.loanDate.toString().contains(search) ||
                            it.returnDate.toString().contains(search)
                            )
        }

        loanList(filteredLoan)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownMenu(onFilterSelected: (String?) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedFilter by rememberSaveable { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        OutlinedTextField(
            value = selectedFilter,
            onValueChange = {selectedFilter = it},
            label = { Text("Status") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .padding(10.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                { Text("All") },
                onClick = {
                    selectedFilter = "All"
                    onFilterSelected(null)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {Text("Active")},
                onClick = {
                    selectedFilter = "Active"
                    onFilterSelected("Active")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {Text("Expired")},
                onClick = {
                    selectedFilter = "Expired"
                    onFilterSelected("Expired")
                    expanded = false
                }
            )
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun loanCard(loan: Loan, modifier: Modifier = Modifier) {
    Card {
        Column {
            Text(text = "id: " + loan.id)
            Text(text = "Book Title: " + loan.bookTitle)
            Text(text = "User Id: " + loan.userId)
            Text(text = "Loan Date: " + loan.loanDate)
            Text(text = "Due Date: " + loan.dueDate)
            Text(text = "Returned Date: " + loan.returnDate)

            when (loan.isExpired) {
                true -> Text(
                    text = "Expired",
                    textAlign = TextAlign.End
                    )
                false -> Text(
                    text = "Active",
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun loanList(loanList: List<Loan>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(loanList) {
            loanCard(loan = it)
        }
    }
}