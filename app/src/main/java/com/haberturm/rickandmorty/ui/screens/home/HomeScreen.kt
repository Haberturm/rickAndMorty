package com.haberturm.rickandmorty.ui.screens.home

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.haberturm.rickandmorty.ui.nav.NavRoute
import com.haberturm.rickandmorty.ui.screens.details.DetailsScreenRoute

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
    Button(onClick = {
        viewModel.onEvent(
            HomeEvent.NavigateTo(DetailsScreenRoute.get(0))
        )
    }) {

    }
}
