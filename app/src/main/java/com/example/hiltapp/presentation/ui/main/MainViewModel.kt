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
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                if (_uiState.value is UiState.Loading) {
                    // Show full-screen loader on initial load
                } else {
                    // For subsequent pages
                    _uiState.update {
                        if (it is UiState.Success) it.copy(isLoadingMore = true) else it
                    }
                }
                myRepository.getData(currentPage).collect { data ->
                    currentData.addAll(data.results)
                    _uiState.value = UiState.Success(currentData, isLoadingMore = false)
                    currentPage++
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}