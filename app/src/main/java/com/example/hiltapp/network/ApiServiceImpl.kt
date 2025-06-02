package com.example.hiltapp.network

import com.example.hiltapp.network.models.Users
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceImpl @Inject constructor(private val apiService: ApiService) : ApiService {
    override fun getGreeting(): String {
        return "Hello from Hilt (via Interface)!"
    }

    override suspend fun getUsers(): List<Users> {
        return try {
            apiService.getUsers()
        } catch (e: Exception) {
            // Log or handle error
            emptyList() // or rethrow or wrap
        }
    }
}