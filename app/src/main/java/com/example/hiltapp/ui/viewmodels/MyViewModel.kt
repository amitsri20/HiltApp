package com.example.hiltapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltapp.network.MyRepository
import com.example.hiltapp.network.models.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<Users>>(emptyList())
    val users: StateFlow<List<Users>> = _users.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            myRepository.getUsers().collect { userList ->
                _users.value = userList
            }
        }
    }
}