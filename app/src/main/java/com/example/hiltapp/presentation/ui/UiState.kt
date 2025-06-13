package com.example.hiltapp.presentation.ui

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(
        val data: List<T>,
        val isLoadingMore: Boolean = false,
        val isEndReached: Boolean = false
    ) : UiState<List<T>>()

    data class Error(val message: String) : UiState<Nothing>()
}
