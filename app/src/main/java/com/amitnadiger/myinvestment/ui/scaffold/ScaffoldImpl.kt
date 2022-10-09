package com.amitnadiger.myinvestment.ui.scaffold

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import kotlinx.coroutines.launch

@Composable
fun ScaffoldImpl(navController: NavHostController,viewModel: FinProductViewModel,screenConfig: ScreenConfig) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    // Create a coroutine scope. Opening of Drawer snackbar should happen in background
    // thread without blocking main thread

   // Log.e("Scafold"," Entry showFabButton = $showFabButton")
    val coroutineScope = rememberCoroutineScope()

    Log.e("ScaffoldImpl","topAppBarTitle - ${screenConfig.topAppBarTitle}")



    // Scaffold Composable
    Scaffold(
        // pass the scaffold state
        scaffoldState = scaffoldState,
        // pass the topbar we created
        topBar = {

            TopBar(
                screenConfig,
                // When menu is clicked open the
                // drawer in coroutine scope
                onMenuClicked = {
                    coroutineScope.launch {
                        // to close use -> scaffoldState.drawerState.close()
                        scaffoldState.drawerState.open()
                    }
                },
                onSearchClicked = {
                    coroutineScope.launch {
                        // to close use -> scaffoldState.drawerState.close()
                        navController.navigate(NavRoutes.SearchProduct.route)
                    }
                }
            )

        },
        content = { padding ->
            Body(navController,viewModel,padding)
        },

        bottomBar = {
            BottomBar(screenConfig) },
        drawerContent = {
            Drawer(screenConfig)
        },

    floatingActionButton = {
        Fab(navController,screenConfig,coroutineScope,viewModel)
    },floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true)


}

