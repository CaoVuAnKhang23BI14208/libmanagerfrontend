package com.exemple.usthlibraryfrontend.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exemple.usthlibraryfrontend.R
import com.exemple.usthlibraryfrontend.model.Role
import com.exemple.usthlibraryfrontend.model.User
import com.exemple.usthlibraryfrontend.model.books
import com.exemple.usthlibraryfrontend.model.users
import com.exemple.usthlibraryfrontend.viewmodel.UserViewModel

@Preview
@Composable
fun UserScreen(
    userViewModel: UserViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val userUiState by userViewModel.uiState.collectAsState()

    val avatarSize = 100.dp
    val filter = listOf(Role.MEMBER, Role.ADMIN)

    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = avatarSize / 2)
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(avatarSize)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
            )  }

        }
        Spacer(modifier = Modifier.height(avatarSize / 2 + 20.dp))

        Text(
            text = "Welcome ABC",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(
                painter = painterResource(R.drawable.mail),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = " abc@gmail.com"
            )
        }

        dropDownMenu(filter, { userViewModel.updateFilter(it) })

        OutlinedTextField(
            value = userUiState.search,
            onValueChange = {userViewModel.updateSearch(it)},
            label = { Text("Search")},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        userList(userViewModel.filteredUser())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownMenu(filter: List<Role>, onFilterSelected: (Role?) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedFilter by rememberSaveable { mutableStateOf("") }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedFilter,
                onValueChange = { selectedFilter = it },
                readOnly = true,
                label = { Text("Role") },
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
                    text = { Text("All")},
                    onClick = {
                        selectedFilter = "All"
                        onFilterSelected(null)
                        expanded = false
                    }
                )
                filter.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            selectedFilter = it.name
                            onFilterSelected(it)
                            expanded = false
                        }
                    )
                }
            }
        }
}

@Composable
fun userCard(user: User, modifier: Modifier = Modifier) {
    Card() {
        Row {
            Image(
                painter = painterResource(R.drawable.avatar),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
            Column {
                Text(text = "Id: " + user.id)
                Text(text = "User Name: " + user.username)
                Text(text = "Email: " + user.email)
                Text(text = "Role: " + user.role)
            }
        }
    }
}

@Composable
fun userList(userList: List<User>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        items(userList) {
            userCard(user = it)
        }
    }
}