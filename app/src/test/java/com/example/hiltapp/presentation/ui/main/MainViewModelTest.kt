package com.example.hiltapp.presentation.ui.main

import app.cash.turbine.test
import com.example.hiltapp.data.repository.MyRepository
import com.example.hiltapp.domain.models.Data
import com.example.hiltapp.presentation.ui.UiState
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private lateinit var myRepository: MyRepository
    private lateinit var mainViewModel: MainViewModel
    var dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        myRepository = mockk()
        mainViewModel = MainViewModel(myRepository)
    }

    @After
    fun tearDown()
    {
        Dispatchers.resetMain()
    }

    @Test
    fun `initially uiState will be empty list`(){
        val data: List<Data> = emptyList()
        (mainViewModel.uiState, data)
    }
}
