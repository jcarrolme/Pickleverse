package com.example.pickleverse.data.core

sealed class CustomResult<T> {
    data class Success<T>(val data: T?) : CustomResult<T>()
    data class Error<T>(val errorType: Throwable) : CustomResult<T>()
}