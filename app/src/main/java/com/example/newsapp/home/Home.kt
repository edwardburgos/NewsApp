package com.example.newsapp.home

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.composables.ItemCards
import com.example.newsapp.sections
import kotlinx.coroutines.Job

@ExperimentalComposeUiApi
@Composable
fun Home(
    navController: NavController,
    exampleViewModel: HomeViewModel,
    openDrawer: () -> Job,
    currentTag: String?,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    val selectedItem by exampleViewModel.navigateToSelectedItem.observeAsState()
    val section by exampleViewModel.section.observeAsState(0)
    val items by exampleViewModel.items.observeAsState(listOf())
    val query by exampleViewModel.query.observeAsState("")

    if (exampleViewModel.currentTag.value != currentTag) exampleViewModel.updateCurrentTag(
        currentTag
    )

    if (null != selectedItem) {
        navController.navigate("detail/${selectedItem}")
        exampleViewModel.displayItemDetailsComplete()
    }

    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(onPress = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    })
                },
            color = MaterialTheme.colors.primary,
            elevation = 8.dp,
        ){
            Row(modifier = Modifier.fillMaxWidth()){
                IconButton(onClick = {
                    openDrawer()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                OutlinedTextField(
                    value = query,
                    leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search") },
                    onValueChange = { exampleViewModel.updateQuery(it) }
                )
            }
        }
        Column {
            TabRow(selectedTabIndex = section) {
                sections.forEachIndexed { index, sectionFound ->
                    Tab(
                        text = { Text(sectionFound.name) },
                        selected = section == index,
                        onClick = {
                            exampleViewModel.updateSection(index)
                            exampleViewModel.getItemsFromFlow(query, sectionFound.id, currentTag)
                        }
                    )
                }
            }
            ItemCards(
                { id -> navController.navigate("detail/${id.replace("/", "*>")}") },
                items,
                keyboardController,
                focusManager
            )
        }
    }
}