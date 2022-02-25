package com.example.newsapp.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.HighlightOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
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
    focusManager: FocusManager,
    configuration: Configuration
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
            color = Color.Transparent,
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    openDrawer()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }) {
                    Icon(
                        Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colors.primary
                    )
                }
                CustomTextField(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                        .border(
                            BorderStroke(1.dp, MaterialTheme.colors.primary),
                            RoundedCornerShape(5.dp)
                        ),
                    paddingLeadingIconEnd = 16.dp,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            modifier = Modifier
                                .padding(start = 16.dp, end = 0.dp, top = 8.dp, bottom = 8.dp)
                        )
                    },
                    value = query,
                    onValueChange = { exampleViewModel.updateQuery(it) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.HighlightOff,
                            contentDescription = "Clean",
                            modifier = Modifier
                                .padding(start = 0.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                                .clickable(enabled = true, onClick = { exampleViewModel.updateQuery("") })
                        )
                    },
                    paddingTrailingIconStart = 16.dp
                )
            }
        }
        Column {
            TabRow(
                selectedTabIndex = section,
                backgroundColor = Color.Transparent
            ) {
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
                focusManager,
                configuration
            )
        }
    }
}