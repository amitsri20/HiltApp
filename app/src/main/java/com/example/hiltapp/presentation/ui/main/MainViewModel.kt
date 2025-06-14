package com.example.hiltapp.presentation.ui.main

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltapp.data.repository.MyRepository
import com.example.hiltapp.domain.models.Data
import com.example.hiltapp.presentation.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private var _uiState = MutableStateFlow<List<Data>>(emptyList())
    var uiState = _uiState.asStateFlow()

    init {
        getUserData()
    }

    fun getUserData(){
        viewModelScope.launch(Dispatchers.Main) {
            myRepository.getGetUserData().collect { userData ->
                _uiState.value = userData
            }
        }
    }
}