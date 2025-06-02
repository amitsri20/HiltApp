package com.example.hiltapp.network

import com.example.hiltapp.network.models.Users
import retrofit2.http.GET

interface ApiService {
    fun getGreeting(): String

    @GET("users")
    suspend fun getUsers(): List<Users>
}