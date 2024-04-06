package com.nativeapp.tutorialpoint.repositories

sealed class ApiResponse<out T : Any>{
    class Success<out T: Any>(val data: T): ApiResponse<T>()

    class ApiError(val message: String): ApiResponse<Nothing>()

    class Failure(val exception: Exception): ApiResponse<Nothing>()
}
