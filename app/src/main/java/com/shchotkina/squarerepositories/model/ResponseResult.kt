package com.shchotkina.squarerepositories.model

import java.lang.Exception

sealed class ResponseResult<out R> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error<out T>(val exception: Exception) : ResponseResult<T>()
}