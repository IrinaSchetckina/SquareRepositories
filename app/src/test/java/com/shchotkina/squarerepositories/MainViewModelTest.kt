package com.shchotkina.squarerepositories

import com.shchotkina.squarerepositories.model.RepositoryItem
import com.shchotkina.squarerepositories.model.ResponseResult
import com.shchotkina.squarerepositories.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @Mock
    private lateinit var repository: AppRepository

    private lateinit var viewModel: MainViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getSquareRepository returns success`() = testDispatcher.runBlockingTest {
        val repositories = listOf<RepositoryItem>()
        `when`(repository.getSquareRepositories()).thenReturn(ResponseResult.Success(repositories))

        viewModel.getSquareRepository()

        assertEquals(
            ListUiState(isLoading = false, repositories = repositories, error = null),
            viewModel.uiState.value
        )
    }

    @Test
    fun `getSquareRepository returns error`() = testDispatcher.runBlockingTest {
        val exception = Exception("Network error")
        `when`(repository.getSquareRepositories()).thenReturn(ResponseResult.Error(exception))

        viewModel.getSquareRepository()

        assertEquals(
            ListUiState(isLoading = false, repositories = emptyList(), error = exception),
            viewModel.uiState.value
        )
    }
}