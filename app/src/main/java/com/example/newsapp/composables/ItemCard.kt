package com.example.newsapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.domain.Content

@Composable
fun ItemCard(
    navigate: (id: String) -> Unit,
    item: Content,
    index: Int
) {
    Card(
        modifier = Modifier
            .clickable(onClick = { navigate(item.id) })
            .padding(
                start = 16.dp,
                top = if (index == 0) 16.dp else 0.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            item.fields.thumbnail?.let {
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                        .padding(bottom = 10.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = item.fields.headline,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = "Published in ${item.webPublicationDate.substringBefore("T")}",
                style = MaterialTheme.typography.body2
            )
        }

    }
}