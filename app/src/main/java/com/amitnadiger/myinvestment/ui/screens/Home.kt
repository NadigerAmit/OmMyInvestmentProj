package com.amitnadiger.myinvestment.ui.screens

import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.graphics.Color

import androidx.navigation.NavHostController

import com.amitnadiger.myinvestment.utility.*

import com.amitnadiger.myinvestment.viewModel.FinProductViewModel

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
fun getScreenConfig4Home():ScreenConfig {
    //Log.e("HomeScvreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = true,
        enableAction = true,
        topAppBarTitle = "MyInvestments", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}


