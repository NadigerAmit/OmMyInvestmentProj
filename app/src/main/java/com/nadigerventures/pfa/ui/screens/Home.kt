package com.nadigerventures.pfa.ui.screens


import android.util.Log

import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.graphics.Color

import androidx.navigation.NavHostController

import com.nadigerventures.pfa.utility.*

import com.nadigerventures.pfa.viewModel.FinProductViewModel

private val TAG = "Home"

@Composable
fun Home(navController: NavHostController,viewModel: FinProductViewModel,padding:PaddingValues) {
    val allProducts by viewModel.allAccounts.observeAsState(listOf())
    //val searchResults by viewModel.searchResults.observeAsState(listOf())
    val displayItemListOfBlankHome = listOf(
        //  "DisplayImage",
        "statementTextForHome",
        "statementText1",
        "maturedItemsText",
        "aboutToMaturedItemsText",
        "normalItemsText",
        "GuideToAddItems",
      //  "ImageToAddItems",
    )
      ProductListScreen(
          navController,
        allProducts = allProducts,
       // searchResults = searchResults,
          padding,
          "ProductDetail",
          displayItemListOfBlankHome
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


