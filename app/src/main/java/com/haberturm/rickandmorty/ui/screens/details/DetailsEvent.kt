package com.haberturm.rickandmorty.ui.screens.details

import com.haberturm.rickandmorty.ui.screens.home.HomeEvent

sealed class DetailsEvent{
    object OnNavigateUp : DetailsEvent()
    object OnRefresh : DetailsEvent()
}
