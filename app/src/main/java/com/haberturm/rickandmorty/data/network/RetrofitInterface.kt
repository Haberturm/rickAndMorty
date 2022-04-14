package com.haberturm.rickandmorty.data.network

import com.haberturm.rickandmorty.data.network.pojo.CharacterResponse
import retrofit2.http.GET

interface RetrofitInterface {
    @GET(Api.CHARACTERS)
    suspend fun getData(): CharacterResponse
}