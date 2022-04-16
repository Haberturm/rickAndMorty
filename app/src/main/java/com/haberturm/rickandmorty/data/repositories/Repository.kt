package com.haberturm.rickandmorty.data.repositories

import com.haberturm.rickandmorty.data.network.pojo.CharacterResponse
import com.haberturm.rickandmorty.data.network.pojo.Results
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getDataList(): Flow<CharacterResponse>
    fun getSingleCharacter(id: Int): Flow<Results>
}