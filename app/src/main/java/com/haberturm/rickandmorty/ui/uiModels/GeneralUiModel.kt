package com.haberturm.rickandmorty.ui.uiModels

import com.haberturm.rickandmorty.data.network.pojo.Results

data class GeneralUiModel(
    val id: Int,
    val name: String,
    val race: String,
    val gender: String,
    val image: String,
)

fun Results.toGeneralUiModel(): GeneralUiModel{
    return GeneralUiModel(
        id = id,
        name = name,
        race = species,
        gender = gender,
        image = image
    )
}