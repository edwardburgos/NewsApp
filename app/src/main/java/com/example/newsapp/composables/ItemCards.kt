package com.example.newsapp.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.domain.Content

@Composable
fun ItemCards(navigate: (id: String) -> Unit, items: List<Content>) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items.size) { index ->
           ItemCard(navigate, items.elementAt(index), index)
        }
    }
}