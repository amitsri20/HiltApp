package com.example.hiltapp.data.api

import com.example.hiltapp.domain.models.Data
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceImpl @Inject constructor(private val apiService: ApiService) : ApiService {
    override fun getGreeting(): String {
        return "Hello from Hilt (via Interface)!"
    }
}