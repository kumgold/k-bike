package com.goldcompany.apps.koreabike.util

sealed class Async<out T> {
    object Loading : Async<Nothing>()
    data class Success<out T>(val data: T) : Async<T>()
}

enum class LoadingState {
    INIT,
    LOADING,
    DONE,
    ERROR
}
