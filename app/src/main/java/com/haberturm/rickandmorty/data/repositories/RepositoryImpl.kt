package com.haberturm.rickandmorty.data.repositories

import com.haberturm.rickandmorty.data.network.RetrofitClient
import com.haberturm.rickandmorty.data.network.pojo.CharacterResponse
import com.haberturm.rickandmorty.data.network.pojo.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl : Repository {
    override fun getDataList(page: Int): Flow<CharacterResponse> = flow {
        val data = RetrofitClient.retrofit.getData(page)
        emit(data)
    }.flowOn(Dispatchers.IO)

    override fun getSingleCharacter(id: Int): Flow<Results> = flow{
        val character = RetrofitClient.retrofit.getSingleCharacter(id)
        emit(character)
    }.flowOn(Dispatchers.IO)
}