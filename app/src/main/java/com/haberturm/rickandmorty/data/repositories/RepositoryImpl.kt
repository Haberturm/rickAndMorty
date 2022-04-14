package com.haberturm.rickandmorty.data.repositories

import com.haberturm.rickandmorty.data.network.RetrofitClient
import com.haberturm.rickandmorty.data.network.pojo.CharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl : Repository {
    override fun getDataList(): Flow<CharacterResponse> = flow {
        val data = RetrofitClient.retrofit.getData()
        emit(data)
    }.flowOn(Dispatchers.IO)
}