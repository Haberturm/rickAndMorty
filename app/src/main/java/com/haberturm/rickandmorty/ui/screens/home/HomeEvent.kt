package com.haberturm.rickandmorty.ui.screens.home


sealed class HomeEvent{
    data class NavigateTo(val route: String) : HomeEvent()
    data class UpdatePageSelectorText(val text: String) : HomeEvent()
    data class ChangeFocus(val isFocused: Boolean) : HomeEvent()
    data class PageSelectorError(val error: Boolean) : HomeEvent()
    object OnRefresh : HomeEvent()
}
