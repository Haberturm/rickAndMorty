package com.haberturm.rickandmorty.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailInfoItem(
    status: String,
    lastLocation: String,
    numOfEpisodes: Int
){
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ){
        Column(Modifier.padding(8.dp)) {
            Text(
                text = "Дополнительная информация:",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Статус: $status")
            Text(text = "Последнее местополодение: $lastLocation")
            Text(text = "Появлялся в $numOfEpisodes эпизодах")
        }
    }
}

@Composable
@Preview
fun DetailPrev(){
    DetailInfoItem(
        status = "Alive",
        lastLocation = "Citadel of Ricks",
        numOfEpisodes = 51
    )
}