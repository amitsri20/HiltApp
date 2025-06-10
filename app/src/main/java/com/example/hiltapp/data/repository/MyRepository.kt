package com.example.hiltapp.data.repository

import com.example.hiltapp.data.api.ApiServiceImpl
import com.example.hiltapp.domain.models.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepository @Inject constructor(
    private val apiService: ApiServiceImpl
) {
    fun getListData(page: Int): Flow<List<Data>> = flow {
        val data = apiService.getListData(page)
        emit(data)
    }

    fun getData(page: Int): Flow<Data> = flow {
        val data = apiService.getData(page)
        emit(data)
    }
}