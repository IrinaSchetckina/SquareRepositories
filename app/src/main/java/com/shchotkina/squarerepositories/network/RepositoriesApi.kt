package com.shchotkina.squarerepositories.network

import com.shchotkina.squarerepositories.model.RepositoryItem
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface RepositoriesApi {

    @GET(value = "/orgs/square/repos")
    suspend fun getSquareRepositories(): Response<List<RepositoryItem>>
}