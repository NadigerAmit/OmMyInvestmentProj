package com.amitnadiger.myinvestment.ui.scaffold

import android.util.Log
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig
import com.amitnadiger.myinvestment.ui.screens.search
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Fab(navController: NavHostController, screenConfig:ScreenConfig, coroutineScope:CoroutineScope,viewModel: FinProductViewModel) {
    if(screenConfig.enableFab ) {

        Log.e("Scafold","showFabButton = True inside ")
        FloatingActionButton(
            onClick = {
                // When clicked open Snackbar
                coroutineScope.launch {
                    if(screenConfig.fabString == "add") {
                        navController.navigate(NavRoutes.AddProduct.route)
                    }
                    if(screenConfig.fabString == "search") {
                        search(viewModel)
                    }
                }
            },backgroundColor = Color.Red) {
            // Simple Text inside FAB
            if(screenConfig.fabString == "add") {
                Icon(Icons.Filled.Add, "add")
            } else if(screenConfig.fabString == "search") {
                Icon(Icons.Filled.Search, "search")
            }
        }
    }
}