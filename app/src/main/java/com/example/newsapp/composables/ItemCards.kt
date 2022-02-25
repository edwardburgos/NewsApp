package com.example.newsapp.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.example.domain.Content

@ExperimentalComposeUiApi
@Composable
fun ItemCards(navigate: (id: String) -> Unit, items: List<Content>, keyboardController: SoftwareKeyboardController?, focusManager: FocusManager) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items.size) { index ->
           ItemCard(navigate, items.elementAt(index), index, keyboardController, focusManager)
        }
    }
}