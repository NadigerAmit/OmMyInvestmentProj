package com.amitnadiger.myinvestment.ui.scaffold

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig
import com.amitnadiger.myinvestment.ui.screens.deletedRecord
import com.amitnadiger.myinvestment.ui.screens.search
import com.amitnadiger.myinvestment.ui.screens.showDeleteAlertDialogGlobal
import com.amitnadiger.myinvestment.viewModel.FinHistoryViewModel
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
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

        Log.e("Scafold","showFabButton = True inside ")
        FloatingActionButton(
            onClick = {
                // When clicked open Snackbar
                coroutineScope.launch {
                    if(screenConfig.fabString == "add") {
                        var accountNumber:String? = null
                        navController.navigate(NavRoutes.AddProduct.route + "/$accountNumber")
                    }
                    if(screenConfig.fabString == "search") {
                        search(finProductViewModel)
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
                    Spacer( modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = "all", color = Color.Black)
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