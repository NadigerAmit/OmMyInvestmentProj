package com.amitnadiger.myinvestment.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.utility.ProductListScreen
import com.amitnadiger.myinvestment.viewModel.FinHistoryViewModel

@Composable
fun History(navController: NavHostController, viewModel: FinHistoryViewModel, padding: PaddingValues) {
    val allProducts by viewModel.allAccounts.observeAsState(listOf())
    //val searchResults by viewModel.searchResults.observeAsState(listOf())
    ProductListScreen(
        navController,
        allProducts = allProducts,
        // searchResults = searchResults,
        padding,"HistoryProductDetail")

}

fun getScreenConfig4History():ScreenConfig = //Log.e("HomeScvreen","getScreenConfig4Home");
    ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = true,
        topAppBarTitle = "   Deleted History", bottomAppBarTitle = "",
        fabString = "Clear all",
    )