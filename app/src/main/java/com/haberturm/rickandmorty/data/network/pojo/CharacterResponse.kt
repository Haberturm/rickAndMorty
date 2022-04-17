package com.haberturm.rickandmorty.data.network.pojo

import com.google.gson.annotations.SerializedName


data class CharacterResponse (

  @SerializedName("info"    ) var info    : Info,
  @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf()

)