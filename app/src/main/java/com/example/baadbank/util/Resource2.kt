package com.example.baadbank.util

sealed class Resource2<out T>(
    val data: T? = null,
    val message: String? = null,
    val loading: Boolean = false,


) {
    class Success<T>(data: T) : Resource2<T>(data = data)
    class Error<T>(message: String, data: T? = null) : Resource2<T>(
        data = data,
        message = message
    )

    class Loading<T>(load: Boolean) : Resource2<T>(loading = load)

}

