package com.amitnadiger.myinvestment.ui.scaffold

import android.content.Context
import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.BaseApplication
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig
import com.amitnadiger.myinvestment.viewModel.FinHistoryViewModel
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import kotlinx.coroutines.launch

@Composable
fun ScaffoldImpl(navController: NavHostController,
                 finProductViewModel: FinProductViewModel,
                 finHistoryViewModel: FinHistoryViewModel,
                 screenConfig: ScreenConfig,context: Context
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    // Create a coroutine scope. Opening of Drawer snackbar should happen in background
    // thread without blocking main thread

   // Log.e("Scafold"," Entry showFabButton = $showFabButton")
    val coroutineScope = rememberCoroutineScope()

    Log.e("ScaffoldImpl","topAppBarTitle - ${screenConfig.topAppBarTitle}")



    if(screenConfig.enableDrawer) {
        // Scaffold Composable
        Scaffold(
            // pass the scaffold state
            scaffoldState = scaffoldState,
            // pass the topbar we created
            topBar = {

                //CenterAlignedTopBar(
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
                Body(navController,finProductViewModel,finHistoryViewModel,padding)
            },

            bottomBar = {
                BottomBar(screenConfig,context) },

            drawerContent = {
                RichDrawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
            },

            floatingActionButton = {
                Fab(navController,screenConfig,coroutineScope,finProductViewModel,finHistoryViewModel)
            },floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true)
    } else {
        // Scaffold Composable
        Scaffold(
            // pass the scaffold state
            scaffoldState = scaffoldState,
            // pass the topbar we created
            topBar = {

                //CenterAlignedTopBar(
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
                Body(navController,finProductViewModel,finHistoryViewModel,padding)
            },

            bottomBar = {
                BottomBar(screenConfig,context) },
            floatingActionButton = {
                Fab(navController,screenConfig,coroutineScope,finProductViewModel,finHistoryViewModel)
            },floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true)
    }

}

