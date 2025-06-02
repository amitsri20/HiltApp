package com.example.hiltapp.network

import com.example.hiltapp.network.models.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepository @Inject constructor(
    private val apiService: ApiServiceImpl
) {
    fun getUsers(): Flow<List<Users>> = flow {
        val users = apiService.getUsers()  // suspend function returns List<User>
        emit(users)
    }
}