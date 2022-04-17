package com.haberturm.rickandmorty.ui.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haberturm.rickandmorty.data.network.DataState
import com.haberturm.rickandmorty.data.repositories.Repository
import com.haberturm.rickandmorty.ui.nav.RouteNavigator
import com.haberturm.rickandmorty.ui.uiModels.toDetailUiModel
import com.haberturm.rickandmorty.ui.uiModels.toGeneralUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val routeNavigator: RouteNavigator,
    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
) : ViewModel(), RouteNavigator by routeNavigator {
    private val id = DetailsScreenRoute.getArgFrom(savedStateHandle)
    private val _dataState: MutableStateFlow<DataState> = MutableStateFlow(DataState.Empty)
    val dataState: StateFlow<DataState> = _dataState.asStateFlow()

    init {
        getCharacter(id)
    }

    fun onEvent(event: DetailsEvent){
        when(event){
            is DetailsEvent.OnNavigateUp ->{
                routeNavigator.navigateUp()
            }
        }
    }


    private fun getCharacter(id: Int) = viewModelScope.launch{
        _dataState.value = DataState.Loading
        repository.getSingleCharacter(id)
            .catch { e ->
                _dataState.value = DataState.Failure(e)
                Log.i("DATA-EXCEPTION", "$e")
            }
            .onEach { data ->
                val uiData = data.toDetailUiModel()
                _dataState.value = DataState.Success(uiData)
            }
            .launchIn(this)

    }
}