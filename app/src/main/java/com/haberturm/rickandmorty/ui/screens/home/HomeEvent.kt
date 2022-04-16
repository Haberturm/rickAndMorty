package com.haberturm.rickandmorty.ui.screens.home


sealed class HomeEvent{
    data class NavigateTo(val route: String) : HomeEvent()
    data class UpdatePageSelectorText(val text: String) : HomeEvent()
}
