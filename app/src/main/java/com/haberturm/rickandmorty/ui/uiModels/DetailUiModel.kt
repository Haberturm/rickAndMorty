package com.haberturm.rickandmorty.ui.uiModels

import com.haberturm.rickandmorty.data.network.pojo.Results

data class DetailUiModel(
    val generalInfo: GeneralUiModel,
    val status: String,
    val lastLocation: String,
    val numOfEpisodes: Int,
)

fun Results.toDetailUiModel(): DetailUiModel{
    val generalInfo = toGeneralUiModel()
    return DetailUiModel(
        generalInfo = generalInfo,
        status = status,
        lastLocation = location.name,
        numOfEpisodes = episode.size
    )
}
