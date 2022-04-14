package com.haberturm.rickandmorty.ui.screens.details

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.haberturm.rickandmorty.ui.nav.NavRoute
import com.haberturm.rickandmorty.ui.nav.getOrThrow

const val KEY_ARG = "ARG"

object DetailsScreenRoute : NavRoute<DetailsViewModel> {

    override val route = "details/{$KEY_ARG}/"

    fun get(index: Int): String = route.replace("{$KEY_ARG}", "$index")

    fun getArgFrom(savedStateHandle: SavedStateHandle) =
        savedStateHandle.getOrThrow<Int>(KEY_ARG)

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(KEY_ARG) { type = NavType.IntType })

    @Composable
    override fun viewModel(): DetailsViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: DetailsViewModel) = DetailsScreen(viewModel)
}

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel
){

}