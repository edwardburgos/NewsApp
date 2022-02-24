package com.example.newsapp

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.detail.Detail
import com.example.newsapp.home.Drawer
import com.example.newsapp.home.Home
import com.example.newsapp.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun AppComposable() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }

    var currentTag by remember { mutableStateOf<String?>(null) }

    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Drawer(
                onDestinationClicked = { id ->
                    scope.launch {
                        drawerState.close()
                        currentTag = id
                    }
                }
            )
        }
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                val homeviewModel = hiltViewModel<HomeViewModel>()
                Home(navController, homeviewModel, openDrawer, currentTag)
            }
            composable("detail/{itemId}") { backStackEntry ->
                Detail(
                    navController,
                    backStackEntry.arguments?.getString("itemId")?.let { it } ?: run { "" })
            }
        }
    }
}