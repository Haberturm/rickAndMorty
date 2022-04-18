package com.haberturm.rickandmorty.ui.screens.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haberturm.rickandmorty.data.network.DataState
import com.haberturm.rickandmorty.data.repositories.Repository
import com.haberturm.rickandmorty.ui.nav.RouteNavigator
import com.haberturm.rickandmorty.ui.uiModels.toGeneralUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
data class PageSelectorState(
    val currentPage: Int,
    val totalPage: Int
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val repository: Repository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), RouteNavigator by routeNavigator {

    private val _dataState: MutableStateFlow<DataState> = MutableStateFlow(DataState.Empty)
    val dataState: StateFlow<DataState> = _dataState.asStateFlow()

    private val _pageSelectorText: MutableStateFlow<String> = MutableStateFlow("")
    val pageSelectorText: StateFlow<String> = _pageSelectorText.asStateFlow()

    private val _pageSelectorFocus: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val pageSelectorFocus: StateFlow<Boolean> = _pageSelectorFocus.asStateFlow()

    private val _pageSelectorError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val pageSelectorError: StateFlow<Boolean> = _pageSelectorError.asStateFlow()

    private val page = HomeScreenRoute.getArgFrom(savedStateHandle)

    private val _pageSelectorState: MutableStateFlow<PageSelectorState> = MutableStateFlow(
        PageSelectorState(page,0)
    )
    val pageSelectorState: StateFlow<PageSelectorState> = _pageSelectorState.asStateFlow()

    init {
        getDataList()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.NavigateTo -> {
                navigateToRoute(event.route)
            }
            is HomeEvent.UpdatePageSelectorText -> {
                _pageSelectorText.value = event.text
            }
            is HomeEvent.ChangeFocus -> {
                _pageSelectorFocus.value = event.isFocused
            }
            is HomeEvent.PageSelectorError -> {
                _pageSelectorError.value = event.error
            }
            is HomeEvent.OnRefresh -> {
                getDataList()
            }
        }
    }

    private fun getDataList() = viewModelScope.launch {
        _dataState.value = DataState.Loading
        repository.getDataList(page = page)
            .catch { e ->
                _dataState.value = DataState.Failure(e)
                Log.i("DATA-EXCEPTION", "$e")
            }
            .onEach { data ->
                val uiData = data.results.map {
                    it.toGeneralUiModel()
                }
                _pageSelectorState.value = _pageSelectorState.value.copy(totalPage = data.info.pages)
                delay(500) // for smooth loading screen
                _dataState.value = DataState.Success(uiData)
            }
            .launchIn(this)
    }

    fun checkPage(selectedPage: Int): Boolean =
        selectedPage in (1..pageSelectorState.value.totalPage)
}