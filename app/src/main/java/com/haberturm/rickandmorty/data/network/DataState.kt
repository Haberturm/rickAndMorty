package com.haberturm.rickandmorty.data.network

sealed class DataState {
    object Loading : DataState()
    class Failure(val e: Throwable) : DataState()
    class Success(val data: Any) : DataState()
    object Empty : DataState()
}