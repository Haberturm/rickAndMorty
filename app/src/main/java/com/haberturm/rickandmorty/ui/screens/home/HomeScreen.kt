package com.haberturm.rickandmorty.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.haberturm.rickandmorty.data.network.DataState
import com.haberturm.rickandmorty.ui.nav.NavRoute
import com.haberturm.rickandmorty.ui.nav.getOrThrow
import com.haberturm.rickandmorty.ui.screens.details.DetailsScreenRoute
import com.haberturm.rickandmorty.ui.theme.SelectedColor
import com.haberturm.rickandmorty.ui.uiModels.GeneralUiModel
import com.haberturm.rickandmorty.ui.views.ErrorView
import com.haberturm.rickandmorty.ui.views.GeneralInfoItem
import com.haberturm.rickandmorty.ui.views.LoadingScreen
import com.haberturm.rickandmorty.ui.views.PageSelector
import com.haberturm.rickandmorty.R

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
                charactersList = (dataState.value as DataState.Success).data as List<GeneralUiModel>, // in this case, it is safe
                detailNavigationAction = fun(id: Int) {
                    viewModel.onEvent(HomeEvent.NavigateTo(DetailsScreenRoute.get(id)))
                },
                pageSelectorTextValue = viewModel.pageSelectorText.collectAsState().value,
                updatePageSelectorTextValue = fun(text: String) {
                    viewModel.onEvent(HomeEvent.UpdatePageSelectorText(text))
                },
                pageSelectorState = viewModel.pageSelectorState.collectAsState().value,
                pageNavigationAction = fun(page: Int) {
                    if (viewModel.checkPage(page)){
                        viewModel.onEvent(HomeEvent.NavigateTo(HomeScreenRoute.get(page)))
                        viewModel.onEvent(HomeEvent.PageSelectorError(false))
                    }else{
                        viewModel.onEvent(HomeEvent.PageSelectorError(true))
                    }
                },
                pageSelectorFocus = viewModel.pageSelectorFocus.collectAsState().value,
                pageSelectorChangeFocus = fun(isFocused: Boolean){
                    viewModel.onEvent(HomeEvent.ChangeFocus(isFocused))
                },
                pageSectorTextFieldError = viewModel.pageSelectorError.collectAsState().value
            )
        }
        is DataState.Loading -> {
            LoadingScreen()
        }
        is DataState.Failure -> {
            ErrorView (refreshAction = {viewModel.onEvent(HomeEvent.OnRefresh)})
        }
        else -> {
            Unit
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    charactersList: List<GeneralUiModel>,
    detailNavigationAction: (Int) -> Unit,
    pageSelectorTextValue: String,
    updatePageSelectorTextValue: (String) -> Unit,
    pageSelectorState: PageSelectorState,
    pageNavigationAction: (Int) -> Unit,
    pageSelectorFocus: Boolean,
    pageSelectorChangeFocus: (Boolean) -> Unit,
    pageSectorTextFieldError: Boolean
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
            .height(LocalConfiguration.current.screenHeightDp.dp)
            .clickable {
        focusManager.clearFocus()
    }) {
        stickyHeader {
            MainHeader(
                text = stringResource(R.string.rick_and_morty_text),
                height = if (listState.firstVisibleItemIndex < 1) 150 else 100
            )
        }
        items(charactersList) { character ->
            Column(Modifier.padding(horizontal = 8.dp)) {
                GeneralInfoItem(
                    name = character.name,
                    race = character.race,
                    gender = character.gender,
                    image = character.image,
                    action = {
                        if (pageSelectorFocus){
                            focusManager.clearFocus()
                        }else{
                            detailNavigationAction(character.id)
                        }
                    }
                )
            }
        }
        item {
            Row(Modifier.padding(4.dp)) {
                PageSelector(
                    currentPageNum = pageSelectorState.currentPage,
                    lastPageNum = pageSelectorState.totalPage,
                    pageSelectorTextValue = pageSelectorTextValue,
                    updatePageSelectorTextValue = updatePageSelectorTextValue,
                    pageNavigationAction = pageNavigationAction,
                    changeFocus = pageSelectorChangeFocus,
                    textFieldError = pageSectorTextFieldError
                )
            }
        }
    }
}

@Composable
fun MainHeader(
    text: String,
    height: Int,
){
    Row(
        Modifier
            .background(SelectedColor)
            .fillMaxWidth()
            .height(height.dp)
            .padding(
                start = dimensionResource(id = R.dimen.default_padding),
                top = 40.dp
            )
            .animateContentSize(),
    ) {
        Text(
            text = text,
            fontSize = 40.sp,
            color = Color.White
        )
    }
}