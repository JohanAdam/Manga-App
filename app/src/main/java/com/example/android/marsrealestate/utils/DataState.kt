package com.example.android.marsrealestate.utils

sealed class DataState<out R> {
    data class Success<out T>(val data: T): DataState<T>()
    data class Failed(val exception: Exception): DataState<Nothing>()
    object Loading: DataState<Nothing>()
}