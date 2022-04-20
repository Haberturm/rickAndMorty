package com.haberturm.rickandmorty.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
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
import com.haberturm.rickandmorty.ui.theme.ClickableColor
import com.haberturm.rickandmorty.ui.uiModels.DetailUiModel
import com.haberturm.rickandmorty.ui.views.DetailInfoItem
import com.haberturm.rickandmorty.ui.views.ErrorView
import com.haberturm.rickandmorty.ui.views.GeneralInfoItem
import com.haberturm.rickandmorty.ui.views.LoadingScreen

const val KEY_ID = "ARG"

object DetailsScreenRoute : NavRoute<DetailsViewModel> {

    override val route = "details/{$KEY_ID}/"

    fun get(id: Int): String = route.replace("{$KEY_ID}", "$id")

    fun getArgFrom(savedStateHandle: SavedStateHandle) =
        savedStateHandle.getOrThrow<Int>(KEY_ID)

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(KEY_ID) { type = NavType.IntType })

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
        TopAppBar(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = ClickableColor
        ) {
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
                LoadingScreen()
            }
            is DataState.Failure -> {
                ErrorView(
                    refreshAction =  {viewModel.onEvent(DetailsEvent.OnRefresh)}
                )
            }
            else -> {
                Unit
            }
        }
        it //to get rid of warning
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