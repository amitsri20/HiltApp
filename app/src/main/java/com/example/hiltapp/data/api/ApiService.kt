package com.example.hiltapp.data.api

import com.example.hiltapp.domain.models.Data
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    fun getGreeting(): String

    @GET("users")
    suspend fun getUserData(): List<Data>
}