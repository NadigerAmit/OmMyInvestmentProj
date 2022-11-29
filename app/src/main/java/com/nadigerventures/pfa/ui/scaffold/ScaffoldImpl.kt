package com.nadigerventures.pfa.ui.scaffold

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.ui.screens.ScreenConfig
import com.nadigerventures.pfa.viewModel.FinHistoryViewModel
import com.nadigerventures.pfa.viewModel.FinProductViewModel
import kotlinx.coroutines.launch

private val TAG = "ScaffoldImpl"
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

    Log.e(TAG,"topAppBarTitle - ${screenConfig.topAppBarTitle}")

    fun searchFunction() {
        Log.e(TAG,"finProductViewModel.allAccounts.value?.size = " +
                "${finProductViewModel.allAccounts.value?.size}" )
        if(finProductViewModel.allAccounts.value?.size!! > 0) {
            navController.navigate(NavRoutes.SearchProduct.route)  {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
            }
        } else {
            Toast.makeText(context, "No investment records yet in DataBase, "
                , Toast.LENGTH_LONG)
                .show()
        }
    }


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
                            searchFunction()
                        }
                    }
                )
            },
            content = { padding ->
                Body(navController,finProductViewModel,finHistoryViewModel,padding)
            },

            bottomBar = {
                BottomBar(screenConfig,context,navController) },

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
                            searchFunction()
                        }
                    }
                )
            },
            content = { padding ->
                Body(navController,finProductViewModel,finHistoryViewModel,padding)
            },

            bottomBar = {
                BottomBar(screenConfig,context,navController) },
            floatingActionButton = {
                Fab(navController,screenConfig,coroutineScope,finProductViewModel,finHistoryViewModel)
            },floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true
        )
    }
}

