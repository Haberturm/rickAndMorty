package com.haberturm.rickandmorty.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haberturm.rickandmorty.R
import com.haberturm.rickandmorty.ui.theme.SelectedColor

@Composable
fun LoadingScreen(){
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        CircularProgressIndicator(color = SelectedColor)
        Text(text = stringResource(R.string.loading_text))
    }
}

@Composable
@Preview
fun LoadingScreenPrev(){
    LoadingScreen()
}