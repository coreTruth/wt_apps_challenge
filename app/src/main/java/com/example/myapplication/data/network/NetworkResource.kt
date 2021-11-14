package com.example.myapplication.data.network

sealed class NetworkResource<out T> {
    data class Success<out T>(val value: T) : NetworkResource<T>()
    object Failure: NetworkResource<Nothing>()
}
