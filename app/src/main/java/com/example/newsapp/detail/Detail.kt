package com.example.newsapp.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun Detail(
    navController: NavController,
    itemId: String,
    exampleViewModel: DetailViewModel = viewModel()
) {
    val item by exampleViewModel.item.observeAsState()
    exampleViewModel.getItemFromFlow(itemId)
    item?.let {
        Text(it.fields.headline)
    }
}