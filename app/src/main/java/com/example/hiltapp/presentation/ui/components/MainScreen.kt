package com.example.hiltapp.presentation.ui.components

import android.R.attr.data
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hiltapp.domain.models.Character
import com.example.hiltapp.presentation.ui.UiState
import com.example.hiltapp.presentation.ui.main.MainViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val uiState by mainViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()?.index ?: 0
                lastVisibleItem
            }
            .distinctUntilChanged()
            .collect { lastVisibleItemIndex ->
                if (uiState is UiState.Success && !(uiState as UiState.Success<Character>).isLoadingMore
                    && !(uiState as UiState.Success<Character>).isEndReached
                    && lastVisibleItemIndex >= (uiState as UiState.Success).data.size - 1) {
                    mainViewModel.loadData()
                }
            }
    }

    when (uiState) {
        is UiState.Loading -> // loading indicator
            Loading()

        is UiState.Error -> // error message
        {
            val message = (uiState as UiState.Error).message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { mainViewModel.loadData() }) {
                        Text("Retry")
                    }
                }
            }
        }

        is UiState.Success -> {
            val state = (uiState as UiState.Success)
            LazyColumn(state = listState) {
                items(state.data.size) { index ->
                    ItemLayout(state.data[index])
                }
                if (state.isLoadingMore) {
                    // Show bottom loading spinner if needed
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Loading...")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ItemLayout(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = character.name,
                modifier = Modifier.padding(16.dp, 8.dp)
            )
            Text(
                text = character.type,
                modifier = Modifier.padding(16.dp, 8.dp)
            )
        }
    }
}
