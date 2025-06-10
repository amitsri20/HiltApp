package com.example.hiltapp.data.api

import com.example.hiltapp.domain.models.Data
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    fun getGreeting(): String

    @GET("character")
    suspend fun getListData(@Query("page") page: Int): List<Data>

    @GET("character")
    suspend fun getData(@Query("page") page: Int): Data
}