package com.haberturm.rickandmorty.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.haberturm.rickandmorty.R
import com.haberturm.rickandmorty.ui.util.Util

@Composable
fun DetailInfoItem(
    status: String,
    lastLocation: String,
    numOfEpisodes: Int
) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.default_padding).value.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier.padding(
                dimensionResource(id = R.dimen.default_padding).value.dp
            )
        ) {
            Text(
                text = "Дополнительная информация:",
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.detail_header_size).value.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(
                    id = R.string.status,
                    status
                )
            )
            Text(
                text = stringResource(
                    id = R.string.location,
                    lastLocation
                )
            )
            Text(
                text = stringResource(
                    id = R.string.num_episodes,
                    numOfEpisodes,
                    Util.endingSelection(numOfEpisodes)
                ),
            )
        }
    }
}

@Composable
@Preview
fun DetailPrev() {
    DetailInfoItem(
        status = "Alive",
        lastLocation = "Citadel of Ricks",
        numOfEpisodes = 51
    )
}