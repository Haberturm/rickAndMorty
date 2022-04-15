package com.haberturm.rickandmorty.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.haberturm.rickandmorty.data.network.DataState
import com.haberturm.rickandmorty.ui.nav.NavRoute
import com.haberturm.rickandmorty.ui.screens.details.DetailsScreenRoute
import com.haberturm.rickandmorty.ui.uiModels.GeneralUiModel
import com.haberturm.rickandmorty.ui.views.Item
import org.jdom2.Content

object HomeScreenRoute : NavRoute<HomeViewModel> {
    override val route = "home/"

    @Composable
    override fun viewModel(): HomeViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: HomeViewModel) = HomeScreen(viewModel)
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel
) {
    val dataState = viewModel.dataState.collectAsState()
    when (dataState.value) {
        is DataState.Success -> {
            Content(
                charactersList = (dataState.value as DataState.Success).data as List<GeneralUiModel>, // in this case it is safe
                navigationAction = { viewModel.onEvent(HomeEvent.NavigateTo(DetailsScreenRoute.get(0))) }
            )
        }
        is DataState.Loading -> {
            //TODO
        }
        is DataState.Failure -> {
            //TODO
        }
        else -> {
            Unit
        }
    }
}

@Composable
private fun Content(
    charactersList: List<GeneralUiModel>,
    navigationAction: () -> Unit
) {
    LazyColumn {
        items(charactersList) { character ->
            Item(
                name = character.name,
                race = character.race,
                gender = character.gender,
                image = character.image,
                action = {
                    navigationAction()
                    /*TODO*/
                }
            )
        }
    }
}
