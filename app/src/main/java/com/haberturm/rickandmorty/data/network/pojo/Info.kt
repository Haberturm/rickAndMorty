package com.haberturm.rickandmorty.data.network.pojo

import com.google.gson.annotations.SerializedName


data class Info (

  @SerializedName("count" ) var count : Int,
  @SerializedName("pages" ) var pages : Int,
  @SerializedName("next"  ) var next  : String? = null,
  @SerializedName("prev"  ) var prev  : String? = null

)