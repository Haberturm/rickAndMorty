package com.haberturm.rickandmorty.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haberturm.rickandmorty.data.network.ApiState
import com.haberturm.rickandmorty.data.repositories.Repository
import com.haberturm.rickandmorty.ui.nav.RouteNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    private val repository: Repository
) : ViewModel(), RouteNavigator by routeNavigator {

    val characters: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    init {
        getDataList()
    }
    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.NavigateTo -> {
                navigateToRoute(event.route)
            }
        }
    }

    private fun getDataList() = viewModelScope.launch {
        characters.value = ApiState.Loading
        repository.getDataList()
            .catch { e ->
                characters.value = ApiState.Failure(e)
                Log.i("DATA-EXCEPTION", "$e")
            }.collect{ data ->
                characters.value = ApiState.Success(data)
                Log.i("DATA", "${(characters.value as ApiState.Success).data}")
            }
    }
}