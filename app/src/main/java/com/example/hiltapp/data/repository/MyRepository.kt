package com.example.hiltapp.data.repository

import android.util.Log
import com.example.hiltapp.data.api.ApiServiceImpl
import com.example.hiltapp.domain.models.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepository @Inject constructor(
    private val apiService: ApiServiceImpl
    //room
) {
    fun getGetUserData(): Flow<List<Data>> = flow {
        try {
            val userData = apiService.getUserData()
            emit(userData)
        }
        catch (e: Exception){
            Log.e("MyRepository", "Error fetching user data", e)
            emit(emptyList())
        }
    }
}