package com.shchotkina.squarerepositories.repository

import com.shchotkina.squarerepositories.model.Owner
import com.shchotkina.squarerepositories.model.RepositoryItem
import com.shchotkina.squarerepositories.model.ResponseResult
import com.shchotkina.squarerepositories.network.RepositoriesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Response

class AppRepositoryTest{
    private lateinit var api: RepositoriesApi
    private lateinit var repository: AppRepository

    @Before
    fun setup() {
        api = Mockito.mock(RepositoriesApi::class.java)
        repository = AppRepository(api)
    }

    @Test
    fun getSquareRepositories_returnsSuccess() = runBlocking {
        val mockResponse = listOf(RepositoryItem(id = 1L, owner = Owner(login = "test", id = 1L), name = "test", fullName = "test", htmlURL = "test", description = "test", url = "test"))
        `when`(api.getSquareRepositories()).thenReturn(Response.success(mockResponse))

        val result = repository.getSquareRepositories()

        assertEquals(ResponseResult.Success(mockResponse), result)
    }

    @Test
    fun getSquareRepositories_returnsError() = runBlocking {
        val exception = RuntimeException("Network error")
        `when`(api.getSquareRepositories()).thenThrow(exception)

        val result = repository.getSquareRepositories()

        assertEquals(ResponseResult.Error<List<RepositoryItem>>(exception), result)
    }
}