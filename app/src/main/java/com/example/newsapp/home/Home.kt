package com.example.newsapp.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.composables.ItemCards
import com.example.newsapp.sections
import kotlinx.coroutines.Job

@Composable
fun Home(
    navController: NavController,
    exampleViewModel: HomeViewModel,
    openDrawer: () -> Job,
    currentTag: String?
) {
    val selectedItem by exampleViewModel.navigateToSelectedItem.observeAsState()
    val section by exampleViewModel.section.observeAsState(0)
    val items by exampleViewModel.items.observeAsState(listOf())

    if (exampleViewModel.currentTag.value != currentTag) exampleViewModel.updateCurrentTag(
        currentTag
    )

    if (null != selectedItem) {
        navController.navigate("detail/${selectedItem}")
        exampleViewModel.displayItemDetailsComplete()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { openDrawer() }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                },
                title = {
                    OutlinedTextField(
                        value = "",
                        textStyle = MaterialTheme.typography.body1,
                        onValueChange = { println(it) }
                    )
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }
    ) {
        Column {
            TabRow(selectedTabIndex = section) {
                sections.forEachIndexed { index, sectionFound ->
                    Tab(
                        text = { Text(sectionFound.name) },
                        selected = section == index,
                        onClick = {
                            exampleViewModel.updateSection(index)
                            exampleViewModel.getItemsFromFlow(null, sectionFound.id, currentTag)
                        }
                    )
                }
            }
            ItemCards(
                { id -> navController.navigate("detail/${id.replace("/", "*>")}") },
                items
            )
        }
    }
}