package com.haberturm.rickandmorty.ui.views


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.size as size
import com.haberturm.rickandmorty.R
import com.haberturm.rickandmorty.ui.theme.AdditionalTextColor
import com.haberturm.rickandmorty.ui.theme.ClickableColor

@Composable
fun Item(
    name: String,
    race: String,
    gender: String,
    image: String,
    action: () -> Unit,
) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                action()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "hero icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, ClickableColor, CircleShape)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = name, fontSize = dimensionResource(id = R.dimen.header_size).value.sp)
                Text(
                    text = "$race, $gender",
                    color = AdditionalTextColor
                )
            }
        }
    }
}

@Composable
@Preview
fun ItemPrev() {
    Item(
        name = "Rick Sanchezz",
        race = "Human",
        gender = "Male",
        image = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/sm/1-a-bomb.jpg",
        {}
    )
}
