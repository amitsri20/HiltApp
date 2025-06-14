package com.example.hiltapp.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hiltapp.domain.models.Data
import com.example.hiltapp.presentation.ui.UiState
import com.example.hiltapp.presentation.ui.main.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val userData = mainViewModel.uiState.collectAsState()

    LazyColumn {
        items(userData.value.size) { index ->
            ItemLayout(userData.value[index])
        }
    }

}

@Composable
fun ItemLayout(data: Data){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column {
            Text(data.name)
            Text(data.email)
            Text(data.phone)
        }
    }
}