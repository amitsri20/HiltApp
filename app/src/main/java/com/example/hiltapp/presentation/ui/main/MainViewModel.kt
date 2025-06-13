package com.example.hiltapp.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltapp.data.repository.MyRepository
import com.example.hiltapp.domain.models.Character
import com.example.hiltapp.presentation.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Character>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Character>>> = _uiState.asStateFlow()

    var currentData = mutableListOf<Character>()

    private var currentPage = 1
    init {
        loadData()
    }

    fun loadData() {
        val currentState = uiState.value

        if(currentPage == 1)
            _uiState.value = UiState.Loading

        if (currentState is UiState.Success && currentState.isEndReached) return

        viewModelScope.launch {
            try {
                if (currentState is UiState.Success) {
                    _uiState.value = currentState.copy(isLoadingMore = true)
                }

                myRepository.getData(currentPage).collect { data ->
                    val isEnd = data.results.isEmpty()
                    currentData.addAll(data.results)
                    _uiState.value = UiState.Success(currentData, isLoadingMore = false, isEndReached = isEnd)

                    if(!isEnd) currentPage++
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}