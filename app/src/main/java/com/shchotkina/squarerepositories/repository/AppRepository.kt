package com.shchotkina.squarerepositories.repository

import com.shchotkina.squarerepositories.model.RepositoryItem
import com.shchotkina.squarerepositories.model.ResponseResult
import com.shchotkina.squarerepositories.network.RepositoriesApi
import javax.inject.Inject

class AppRepository @Inject constructor(private val api: RepositoriesApi) {

    suspend fun getSquareRepositories(): ResponseResult<List<RepositoryItem>> {
        return try {
            val response = api.getSquareRepositories()
            if (response.isSuccessful) {
                val repositories = response.body()
                if (repositories != null) {
                    ResponseResult.Success(repositories)
                } else {
                    ResponseResult.Error(Exception("Empty response"))
                }
            } else {
                ResponseResult.Error(Exception("Network error"))
            }
        } catch (e: Exception) {
            ResponseResult.Error(e)
        }
    }
}