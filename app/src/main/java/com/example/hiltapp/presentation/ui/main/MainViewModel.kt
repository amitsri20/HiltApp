package com.example.hiltapp.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltapp.data.repository.MyRepository
import com.example.hiltapp.domain.models.Character
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

    private val _data = MutableStateFlow<List<Character>>(emptyList())
    val data: StateFlow<List<Character>> = _data.asStateFlow()

    private var currentPage = 1
    public var isLoading = false
    init {
        loadData()
    }

    fun loadData() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            try {
                myRepository.getData(currentPage).collect { data ->
                    _data.update { it + data.results }
                    currentPage++
                }
            } finally {
                isLoading = false
            }
        }
    }
}