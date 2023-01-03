package com.nadigerventures.pfa.ui.scaffold

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.ui.screens.ScreenConfig
import com.nadigerventures.pfa.ui.screens.search
import com.nadigerventures.pfa.viewModel.FinHistoryViewModel
import com.nadigerventures.pfa.viewModel.FinProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

var showDeleteAllAlertDialogGlobal =  mutableStateOf(false)
@Composable
fun Fab(navController: NavHostController,
        screenConfig:ScreenConfig,
        coroutineScope:CoroutineScope,
        finProductViewModel: FinProductViewModel,
        finHistoryViewModel: FinHistoryViewModel) {
    if(screenConfig.enableFab ) {
        if(showDeleteAllAlertDialogGlobal.value){
            showAlert(onDismiss ={},finHistoryViewModel)
        }

        //Log.i("Scafold","showFabButton = True inside ")
        FloatingActionButton(
            onClick = {
                // When clicked open Snackbar
                coroutineScope.launch {
                    if(screenConfig.fabString == "add") {
                        var accountNumber:String? = null
                        val triggeringScreen = "home"
                        navController.navigate(NavRoutes.AddProduct.route + "/$accountNumber"+"/$triggeringScreen") {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            //restoreState = true
                        }
                    }
                    if(screenConfig.fabString == "search") {
                      //  search(finProductViewModel)
                    }
                    if(screenConfig.fabString == "Clear all") {
                        showDeleteAllAlertDialogGlobal.value = true
                    }
                }
            },backgroundColor =screenConfig.fabColor) {
            // Simple Text inside FAB
            when (screenConfig.fabString) {
                "add" -> {
                    Icon(Icons.Filled.Add, "add")
                }
                "search" -> {
                    Icon(Icons.Filled.Search, "search")
                }
                "Clear all" -> {
                    Icon(Icons.Filled.Delete, "delete")
                }
            }
        }
    }
}

@Composable
private fun showAlert(onDismiss: () -> Unit,
                      finHistoryViewModel: FinHistoryViewModel,
    ) {
    AlertDialog(
        title = { Text(text = "Are you sure to delete whole history ?",color = Color.Red) },
        text = { Text(text = "All the items will be permanently deleted from app" +
                "")},
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                finHistoryViewModel.deleteAllFromHistory()
                showDeleteAllAlertDialogGlobal.value = false
            })
            { Text(text = "Continue delete",color = Color.Red) }

        },
        shape  = MaterialTheme.shapes.medium,
        dismissButton = {
            onDismiss()
            TextButton(onClick = {
                showDeleteAllAlertDialogGlobal.value = false
            })
            { Text(text = "Cancel delete") }
        },
        properties= DialogProperties(dismissOnBackPress = true,
            dismissOnClickOutside=true)
    )
}