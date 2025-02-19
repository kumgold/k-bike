package com.goldcompany.apps.koreabike.util

sealed class Async<out T> {
    data object Loading : Async<Nothing>()
    data class Success<out T>(val data: T) : Async<T>()
    data class Error(val errorMessage: Int) : Async<Nothing>()
}

enum class UIState {
    INIT,
    LOADING,
    DONE,
    ERROR
}
