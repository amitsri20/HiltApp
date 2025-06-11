package com.example.hiltapp.presentation.ui.main
import app.cash.turbine.test
import com.example.hiltapp.data.repository.MyRepository
import com.example.hiltapp.domain.models.Character
import com.example.hiltapp.domain.models.Data
import com.example.hiltapp.presentation.ui.UiState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var repository: MyRepository
    private lateinit var viewModel: MainViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        coEvery { repository.getData(any()) } returns flowOf(Data(emptyList()))
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Success`() = runTest {
        viewModel.uiState.test {
            println("Initial emission: ${awaitItem()}")
        }
        assertTrue(viewModel.uiState.value is UiState.Success)
    }

    @Test
    fun `loadData emits Success state on successful first load`() = runTest {
        val dataList = listOf(
            Character(id = 1, name = "Rick", "", "", "", "", "", emptyList(), "", "")
        )
        val data = Data(dataList)
        coEvery { repository.getData(any()) } returns flowOf(data)


        viewModel.uiState.test {
            println("Initial emission: ${awaitItem()}")

            viewModel.loadData()
            val loadingState = awaitItem()
            println("Second emission: $loadingState")
            assertTrue(loadingState is UiState.Loading)

            val successState = awaitItem()
            println("Third emission: $successState")

            assertTrue(successState is UiState.Success)
            val success = successState as UiState.Success
            assertEquals(dataList, success.data)
            assertFalse(success.isLoadingMore)
        }
    }

    @Test
    fun `loadData appends data on subsequent loads`() = runTest {
        val firstPage = listOf(
            Character(id = 1, name = "Rick", "", "", "", "", "", emptyList(), "", "")
        )
        val secondPage = listOf(
            Character(id = 2, name = "Morty", "", "", "", "", "", emptyList(), "", "")
        )

        // Mock repository
        coEvery { repository.getData(2) } returns flowOf(Data(firstPage))
        coEvery { repository.getData(3) } returns flowOf(Data(secondPage))

        // Collect emissions
        viewModel.uiState.test {
            println("Initial emission: ${awaitItem()}")

            // First load
            viewModel.loadData()

            // 1. Loading
            val loadingState = awaitItem()
            assertTrue(loadingState is UiState.Loading)

            // 2. Success with firstPage
            val firstSuccess = awaitItem()
            assertTrue(firstSuccess is UiState.Success)
            assertEquals(firstPage, (firstSuccess as UiState.Success).data)

            // Second load
            viewModel.loadData()

            // 3. LoadingMore (optional)
            val nextItem = awaitItem()
            // If your ViewModel doesn't emit LoadingMore, fallback here
            assertTrue(nextItem is UiState.Loading)

            // 4. Success with firstPage + secondPage
            val finalSuccess = awaitItem()
            assertTrue(finalSuccess is UiState.Success)
            val combinedData = firstPage + secondPage
            assertEquals(combinedData, (finalSuccess as UiState.Success).data)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadData emits Error state on exception`() = runTest {
        coEvery { repository.getData(any()) } throws Exception("Network error")

        viewModel.uiState.test {
            viewModel.loadData()
            println("Initial emission: ${awaitItem()}")
            skipItems(1) // skip initial Loading
            val errorState = awaitItem()
            assertTrue(errorState is UiState.Error)
            val error = errorState as UiState.Error
            assertEquals("Network error", error.message)
        }
    }
}
