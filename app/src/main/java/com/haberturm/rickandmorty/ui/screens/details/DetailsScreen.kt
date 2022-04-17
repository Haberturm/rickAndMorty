package com.haberturm.rickandmorty.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.haberturm.rickandmorty.R
import com.haberturm.rickandmorty.data.network.DataState
import com.haberturm.rickandmorty.ui.nav.NavRoute
import com.haberturm.rickandmorty.ui.nav.getOrThrow
import com.haberturm.rickandmorty.ui.uiModels.DetailUiModel
import com.haberturm.rickandmorty.ui.views.DetailInfoItem
import com.haberturm.rickandmorty.ui.views.GeneralInfoItem

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
    Scaffold(topBar = {
        TopAppBar() {
            IconButton(onClick = { viewModel.onEvent(DetailsEvent.OnNavigateUp) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "Back"
                )
            }
        }
    }) {
        val dataState = viewModel.dataState.collectAsState()
        when (dataState.value) {
            is DataState.Success -> {
                Content(characterData = (dataState.value as DataState.Success).data as DetailUiModel)
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
}

@Composable
private fun Content(
    characterData: DetailUiModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.default_padding).value.dp)
    ) {
        GeneralInfoItem(
            name = characterData.generalInfo.name,
            race = characterData.generalInfo.race,
            gender = characterData.generalInfo.gender,
            image = characterData.generalInfo.image,
            action = {Unit}
        )
        DetailInfoItem(
            status = characterData.status,
            lastLocation = characterData.lastLocation,
            numOfEpisodes = characterData.numOfEpisodes
        )


    }
}