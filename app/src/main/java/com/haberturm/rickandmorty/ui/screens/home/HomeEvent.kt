package com.haberturm.rickandmorty.ui.screens.home

import com.haberturm.rickandmorty.ui.nav.NavRoute

sealed class HomeEvent{
    data class NavigateTo(val route: String) : HomeEvent()
}
