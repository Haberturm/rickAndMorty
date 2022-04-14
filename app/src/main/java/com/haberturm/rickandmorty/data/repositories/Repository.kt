package com.haberturm.rickandmorty.data.repositories

import com.haberturm.rickandmorty.data.network.pojo.CharacterResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getDataList(): Flow<CharacterResponse>
}