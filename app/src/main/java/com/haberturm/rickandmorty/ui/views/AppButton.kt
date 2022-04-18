package com.haberturm.rickandmorty.ui.views

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.haberturm.rickandmorty.ui.theme.ClickableColor

@Composable
fun AppButton(
    text: String,
    action: () -> Unit
){
    Button(
        onClick = { action()},
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = ClickableColor
        )
    ) {
        Text(
            text = text,
        )
    }
}