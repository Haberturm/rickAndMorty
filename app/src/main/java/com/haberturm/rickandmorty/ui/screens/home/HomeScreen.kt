package com.haberturm.rickandmorty.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.haberturm.rickandmorty.data.network.DataState
import com.haberturm.rickandmorty.ui.nav.NavRoute
import com.haberturm.rickandmorty.ui.nav.getOrThrow
import com.haberturm.rickandmorty.ui.screens.details.DetailsScreenRoute
import com.haberturm.rickandmorty.ui.uiModels.GeneralUiModel
import com.haberturm.rickandmorty.ui.views.GeneralInfoItem
import com.haberturm.rickandmorty.ui.views.PageSelector

const val KEY_PAGE = "PAGE"
object HomeScreenRoute : NavRoute<HomeViewModel> {
    override val route = "home/{$KEY_PAGE}/"

    fun get(index: Int): String = route.replace("{$KEY_PAGE}", "$index")

    fun getArgFrom(savedStateHandle: SavedStateHandle) =
        savedStateHandle.getOrThrow<Int>(KEY_PAGE)

    override fun getArguments(): List<NamedNavArgument> = listOf(
        navArgument(KEY_PAGE) {
            type = NavType.IntType
            defaultValue = 1 // mean first page
        })

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
                detailNavigationAction = fun(id: Int) {
                    viewModel.onEvent(HomeEvent.NavigateTo(DetailsScreenRoute.get(id)))
                },
                pageSelectorTextValue = viewModel.pageSelectorText.collectAsState().value,
                updatePageSelectorTextValue = fun(text: String) {
                    viewModel.onEvent(HomeEvent.UpdatePageSelectorText(text))
                },
                pageSelectorState = viewModel.pageSelectorState.collectAsState().value,
                pageNavigationAction = fun(page: Int) {
                    viewModel.onEvent(HomeEvent.NavigateTo(HomeScreenRoute.get(page)))
                },
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
    detailNavigationAction: (Int) -> Unit,
    pageSelectorTextValue: String,
    updatePageSelectorTextValue: (String) -> Unit,
    pageSelectorState: PageSelectorState,
    pageNavigationAction: (Int) -> Unit,
) {
    LazyColumn {
        item {
            Row(Modifier.padding(4.dp)) {
                PageSelector(
                    currentPageNum = pageSelectorState.currentPage,
                    lastPageNum = pageSelectorState.totalPage,
                    pageSelectorTextValue = pageSelectorTextValue,
                    updatePageSelectorTextValue = updatePageSelectorTextValue,
                    pageNavigationAction = pageNavigationAction
                )
            }
        }
        items(charactersList) { character ->
            Column(Modifier.padding(horizontal = 8.dp)) {
                GeneralInfoItem(
                    name = character.name,
                    race = character.race,
                    gender = character.gender,
                    image = character.image,
                    action = {
                        detailNavigationAction(character.id)
                    }
                )
            }
        }
    }
}
