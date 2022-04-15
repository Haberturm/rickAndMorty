package com.haberturm.rickandmorty.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haberturm.rickandmorty.data.network.DataState
import com.haberturm.rickandmorty.data.repositories.Repository
import com.haberturm.rickandmorty.ui.nav.RouteNavigator
import com.haberturm.rickandmorty.ui.uiModels.toGeneralUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val repository: Repository
) : ViewModel(), RouteNavigator by routeNavigator {

    private val _dataState: MutableStateFlow<DataState> = MutableStateFlow(DataState.Empty)
    val dataState: StateFlow<DataState> = _dataState.asStateFlow()

    init {
        getDataList()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.NavigateTo -> {
                navigateToRoute(event.route)
            }
        }
    }

    private fun getDataList() = viewModelScope.launch {
        _dataState.value = DataState.Loading
        repository.getDataList()
            .catch { e ->
                _dataState.value = DataState.Failure(e)
                Log.i("DATA-EXCEPTION", "$e")
            }.collect { data ->
                val uiData = data.results.map {
                    it.toGeneralUiModel()
                }
                _dataState.value = DataState.Success(uiData)
                Log.i("DATA", "${(_dataState.value as DataState.Success).data}")
            }
    }
}