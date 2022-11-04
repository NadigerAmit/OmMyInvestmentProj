package com.amitnadiger.myinvestment.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.utility.*
import com.amitnadiger.myinvestment.utility.DateUtility.Companion.getNumberOfDaysBetweenTwoDays
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import java.text.NumberFormat
import java.util.*


@Composable
fun Home(navController: NavHostController,viewModel: FinProductViewModel,padding:PaddingValues) {
    val allProducts by viewModel.allAccounts.observeAsState(listOf())
    //val searchResults by viewModel.searchResults.observeAsState(listOf())
      ProductListScreen(
          navController,
        allProducts = allProducts,
       // searchResults = searchResults,
          padding,
      "ProductDetail")

}
fun getScreenConfig4Home():ScreenConfig {
    //Log.e("HomeScvreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = true,
        topAppBarTitle = "My-Investments", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}


