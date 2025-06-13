package com.example.hiltapp.data.repository

import app.cash.turbine.test
import com.example.hiltapp.data.api.ApiServiceImpl
import com.example.hiltapp.domain.models.Character
import com.example.hiltapp.domain.models.Data
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MyRepositoryTest {
    // Mock for the ApiService
    private lateinit var apiService: ApiServiceImpl

    // Instance of the class we are testing
    private lateinit var myRepository: MyRepository

    @Before
    fun setUp() {
        // Initialize the mock before each test
        apiService = mockk()
        // Create an instance of MyRepository with the mocked ApiService
        myRepository = MyRepository(apiService)
    }

    @Test
    fun `getData should emit single data object from apiService`() = runTest {
        // 1. Arrange
        val page = 1 // Or some ID, depending on your API
        val dataList = listOf(
            Character(id = 1, name = "Rick", "", "", "", "", "", emptyList(), "", "")
        )
        val expectedDataObject = Data(dataList)

        coEvery { apiService.getData(page) } returns expectedDataObject

        // 2. Act & 3. Assert
        myRepository.getData(page).test {
            assertEquals(expectedDataObject, awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { apiService.getData(page) }
    }

    @Test
    fun `getData should propagate exceptions from apiService`() = runTest {
        // 1. Arrange
        val page = 1
        val expectedException = RuntimeException("API Error for single item")

        coEvery { apiService.getData(page) } throws expectedException

        // 2. Act & 3. Assert
        myRepository.getData(page).test {
            assertEquals(expectedException, awaitError())
        }

        coVerify(exactly = 1) { apiService.getData(page) }
    }
}