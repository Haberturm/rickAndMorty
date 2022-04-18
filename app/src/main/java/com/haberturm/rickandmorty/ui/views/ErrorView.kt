package com.haberturm.rickandmorty.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.haberturm.rickandmorty.R

@Composable
fun ErrorView(
    refreshAction: () -> Unit
){
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.error_text),
            fontSize = dimensionResource(id = R.dimen.header_size).value.sp,
            textAlign = TextAlign.Center
        )
        AppButton(
            text = stringResource(R.string.refresh_text),
            action = refreshAction
        )
    }
}

@Composable
@Preview
fun ErrorPrev(){
    ErrorView({})
}