package com.haberturm.rickandmorty.ui.uiModels

import com.haberturm.rickandmorty.data.network.pojo.Results

data class GeneralUiModel(
    val name: String,
    val race: String,
    val gender: String,
    val image: String,
)

fun Results.toGeneralUiModel(): GeneralUiModel{
    return GeneralUiModel(
        name = name,
        race = species,
        gender = gender,
        image = image
    )
}