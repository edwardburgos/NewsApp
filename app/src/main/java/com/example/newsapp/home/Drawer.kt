package com.example.newsapp.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.TagPair

val tags = listOf(
    TagPair(null, "No filter"),
    TagPair("business/cost-of-living-crisis", "Cost of living crisis"),
    TagPair("business/chinese-economy", "Chinese economy"),
    TagPair("music/kanyewest", "Kanye West"),
    TagPair("music/2pac", "Tupac Shakur"),
    TagPair("music/bad-bunny", "Bad Bunny")
)

@Composable
fun Drawer(
    onDestinationClicked: (id: String?) -> Unit,
    currentTag: String?
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        tags.forEachIndexed { index, tag ->
            Text(
                text = tag.name,
                modifier = Modifier
                    .clickable { onDestinationClicked(tag.id) }
                    .padding(bottom = 16.dp),
                color = if (currentTag != tag.id) Color.Gray else MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h5.plus(TextStyle(fontWeight = FontWeight.SemiBold))
            )
        }
    }
}