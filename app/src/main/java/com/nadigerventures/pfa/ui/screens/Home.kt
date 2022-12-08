package com.nadigerventures.pfa.ui.screens


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
          padding,
          "ProductDetail",
          displayItemListOfBlankHome
      )
}

fun getScreenConfig4Home():ScreenConfig {
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


