package com.exemple.usthlibraryfrontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exemple.usthlibraryfrontend.model.Role
import com.exemple.usthlibraryfrontend.model.User
import com.exemple.usthlibraryfrontend.model.users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserUiState(
    val users: List<User> = emptyList(),
    val search: String = "",
    val filter: Role? = null
)

class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState

    private val userList = mutableListOf<User>()
    init {
        loadInitUser()
    }

    private fun loadInitUser() {
        viewModelScope.launch {
            userList.addAll(users)
            _uiState.update {
                it.copy(
                    users = userList
                )
            }
        }
    }

    fun updateFilter(role: Role?) {
        _uiState.update {
            it.copy(filter = role)
        }
    }

    fun updateSearch(search: String) {
        _uiState.update {
            it.copy(search = search)
        }
    }

    fun filteredUser(): List<User> {
        val state = _uiState.value
        return state.users.filter {
            (state.filter == null || state.filter == it.role) &&
                    (state.search.isEmpty() || it.id.toString().contains(state.search))
        }
    }
}