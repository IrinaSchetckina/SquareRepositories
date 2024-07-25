package com.shchotkina.squarerepositories.model

import com.google.gson.annotations.SerializedName

data class RepositoryItem (
    val id: Long = 0L,
    val name: String = "",
    @SerializedName("full_name")
    val fullName: String = "",
    val private: Boolean = false,
    val owner: Owner,
    @SerializedName("html_url")
    val htmlURL: String = "",
    val description: String? = "",
    val url: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("watchers_count")
    val watchersCount: Long = 0L,
    val language: Any? = null
)

data class Owner (
    val login: String = "",
    val id: Long = 0L,
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
)