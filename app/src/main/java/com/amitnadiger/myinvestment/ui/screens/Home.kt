package com.amitnadiger.myinvestment.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.base.getActivity

import com.amitnadiger.myinvestment.utility.*

import com.amitnadiger.myinvestment.viewModel.FinProductViewModel

private val TAG = "Home"

@Composable
fun Home(navController: NavHostController,viewModel: FinProductViewModel,padding:PaddingValues) {
    val allProducts by viewModel.allAccounts.observeAsState(listOf())
    //val searchResults by viewModel.searchResults.observeAsState(listOf())
      ProductListScreen(
          navController,
        allProducts = allProducts,
       // searchResults = searchResults,
          padding,
      "ProductDetail"
      )
}


fun onBackArrowPressed() {
    Log.e(TAG,"onBackArrowPressed")
    /*
    var backPressed = 0L
      val finish: () -> Unit = {
    Log.e(TAG,"finish called")
    if (backPressed + 3000 > System.currentTimeMillis()) {
        Log.e(TAG,"finishAndRemoveTask called")
        context.getActivity()?.finishAndRemoveTask()
    } else {
        Toast.makeText(context, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
    }
    backPressed = System.currentTimeMillis() }
    finish()

     */
}

fun getScreenConfig4Home():ScreenConfig {
    //Log.e("HomeScvreen","getScreenConfig4Home");
    /*


     */
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = true,
        screenOnBackPress = "ExitApp",
        enableAction = true,
        topAppBarTitle = "Investments", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}


