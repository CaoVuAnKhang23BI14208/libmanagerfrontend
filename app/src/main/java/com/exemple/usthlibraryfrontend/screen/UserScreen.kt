package com.exemple.usthlibraryfrontend.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exemple.usthlibraryfrontend.R
import com.exemple.usthlibraryfrontend.model.AuthUser
import com.exemple.usthlibraryfrontend.model.Role
import com.exemple.usthlibraryfrontend.viewmodel.UserViewModel
import androidx.compose.runtime.collectAsState
import com.exemple.usthlibraryfrontend.model.User

@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    onForceLogout: () -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    val avatarSize = 100.dp
    val filterOptions = listOf(Role.MEMBER, Role.ADMIN)
    var search by rememberSaveable { mutableStateOf("") }
    var selectedFilter by rememberSaveable { mutableStateOf<Role?>(null) }

    val userList by userViewModel.userList.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()
    val errorMessage by userViewModel.errorMessage.collectAsState()

    LaunchedEffect(key1 = search, key2 = selectedFilter) {
        userViewModel.fetchAllUsers(search, selectedFilter)
    }
    LaunchedEffect(Unit) {
        userViewModel.forceLogout.collect {
            onForceLogout()
        }
    }

    // ✅ Toàn màn hình có thể cuộn
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ảnh nền + avatar
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
                )
            }
        }

        Spacer(modifier = Modifier.height(avatarSize / 2 + 20.dp))

        // Thông tin user
        Text(
            text = "Welcome ${userViewModel.userName.collectAsState().value}",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                userViewModel.logout()
                onForceLogout()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) { Text("Logout") }
        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.mail),
                contentDescription = "Email Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = userViewModel.userEmail.collectAsState().value)
        }

        Spacer(modifier = Modifier.height(20.dp))

//        // Thanh tìm kiếm + filter
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            OutlinedTextField(
//                value = search,
//                onValueChange = { search = it },
//                label = { Text("Search by name") },
//                modifier = Modifier.weight(1f),
//                maxLines = 1
//            )
//            DropDownMenu(filterOptions, onFilterSelected = { selectedFilter = it })
        //}

        Spacer(modifier = Modifier.height(16.dp))

        // Loading / Error / List
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                UserList(userList = userList)
            }
        }
    }
}

@Composable
fun UserList(userList: List<User>, modifier: Modifier = Modifier) {
    if (userList.isEmpty()) {
        Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text("No users found.")
        }
    } else {
        Column(modifier = modifier.fillMaxWidth()) {
            userList.forEach { user ->
                UserCard(user = user)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(filter: List<Role>, onFilterSelected: (Role?) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf("All") }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Role") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("All") },
                onClick = {
                    selectedText = "All"
                    onFilterSelected(null)
                    expanded = false
                }
            )
            filter.forEach { role ->
                DropdownMenuItem(
                    text = { Text(text = role.name) },
                    onClick = {
                        selectedText = role.name
                        onFilterSelected(role)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun UserCard(user: User, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp).fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.avatar),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = user.fullname, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = user.email, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))

            }
        }
    }
}
