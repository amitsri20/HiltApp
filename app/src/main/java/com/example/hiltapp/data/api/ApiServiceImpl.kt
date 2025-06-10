package com.example.hiltapp.data.api

import com.example.hiltapp.domain.models.Data
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceImpl @Inject constructor(private val apiService: ApiService) : ApiService {
    override fun getGreeting(): String {
        return "Hello from Hilt (via Interface)!"
    }

    override suspend fun getListData(page: Int): List<Data> {
        return try {
            apiService.getListData(page)
        } catch (e: Exception) {
            // Log or handle error
            emptyList() // or rethrow or wrap
        }
    }

    override suspend fun getData(page: Int): Data {
        return try {
            apiService.getData(page)
        } catch (e: Exception) {
            // Log or handle error
            return Data(emptyList())
        }
    }
}