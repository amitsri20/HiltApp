package com.example.hiltapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hiltapp.ui.viewmodels.MyViewModel
import androidx.compose.runtime.getValue

@Composable
fun UserScreen(userViewModel: MyViewModel) {
    val users by userViewModel.users.collectAsState()

    val newUsers = users + users + users
    LazyColumn {
        items(newUsers.size) { index ->
            Text(
                text = newUsers[index].name,
                modifier = Modifier.padding(16.dp)
            )

        }
    }
}
