package com.haberturm.rickandmorty.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.haberturm.rickandmorty.ui.screens.details.DetailsScreenRoute
import com.haberturm.rickandmorty.ui.screens.home.HomeScreenRoute

@Composable
fun ScreenHolder(navHostController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navHostController,
        startDestination = HomeScreenRoute.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        HomeScreenRoute.composable(this, navHostController)
        DetailsScreenRoute.composable(this, navHostController)
    }
}