package com.exemple.usthlibraryfrontend.viewmodel

import android.icu.text.StringSearch
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exemple.usthlibraryfrontend.model.Loan
import com.exemple.usthlibraryfrontend.model.LoanRequest
import com.exemple.usthlibraryfrontend.model.loanRequest
import com.exemple.usthlibraryfrontend.model.loans
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoanUiState(
    val selectedTab: Int = 0,
    val activeLoans: List<Loan> = emptyList(),
    val loanRequests: List<LoanRequest> = emptyList(),
    val search: String = "",
    val selectedFilter: String? = null
)

@RequiresApi(Build.VERSION_CODES.O)
class LoanViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoanUiState())
    val uiState: StateFlow<LoanUiState> = _uiState.asStateFlow()
    private val requestList = mutableListOf<LoanRequest>()
    private val activeLoanList = mutableListOf<Loan>()

    init {
        loadInitData()
    }
    private fun loadInitData() {
        viewModelScope.launch {
            requestList.addAll(loanRequest)
            activeLoanList.addAll(loans)
            _uiState.update {
                it.copy(
                    activeLoans = activeLoanList.toList(),
                    loanRequests = requestList.toList()
                )
            }
        }
    }

    fun selectTab(index: Int) {
        _uiState.update {
            it.copy(selectedTab = index)
        }
    }

    fun updateSearch(search: String) {
        _uiState.update {
            it.copy(search = search)
        }
    }

    fun updateFilter(filter: String?) {
        _uiState.update {
            it.copy(selectedFilter = filter)
        }
    }

    fun filteredLoans(): List<Loan> {
        val state = _uiState.value
        return state.activeLoans.filter {
            val selectedFilter = when(state.selectedFilter) {
                "Active" -> !it.isExpired
                "Expired" -> it.isExpired
                else -> true
            }
            val matchSearch = state.search.isBlank() ||
                    it.id.toString().contains(state.search, ignoreCase = true) ||
                    it.bookTitle.contains(state.search, ignoreCase = true) ||
                    it.userId.toString().contains(state.search, ignoreCase = true) ||
                    it.loanDate.toString().contains(state.search, ignoreCase = true) ||
                    it.returnDate.toString().contains(state.search, ignoreCase = true)

            selectedFilter && matchSearch
        }
    }

    fun approveLoanRequest(request: LoanRequest) {
        viewModelScope.launch {
            requestList.remove(request)
            val newId = (activeLoanList.maxOfOrNull { it.id } ?: 0) + 1
            val newLoan = Loan(
                id = newId,
                bookTitle = request.bookTitle,
                userId = request.userId,
                loanDate = request.requestDate,
                dueDate = request.returnDate,
                returnDate = null
            )
            activeLoanList.add(newLoan)
            _uiState.update {
                it.copy(
                    activeLoans = activeLoanList.toList(),
                    loanRequests = requestList.toList()
                )
            }
        }
    }

    fun rejectLoanRequest(request: LoanRequest) {
        viewModelScope.launch {
            requestList.remove(request)
            _uiState.update {
                it.copy(
                    loanRequests = requestList.toList()
                )
            }
        }
    }
}