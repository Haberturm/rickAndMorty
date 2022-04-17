package com.haberturm.rickandmorty.data.network

import com.haberturm.rickandmorty.data.network.pojo.CharacterResponse
import com.haberturm.rickandmorty.data.network.pojo.Results
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET(Api.CHARACTERS)
    suspend fun getData(@Query("page") page:Int): CharacterResponse

    @GET(Api.SINGLE_CHARACTER)
    suspend fun getSingleCharacter(@Path("id") id: Int): Results
}