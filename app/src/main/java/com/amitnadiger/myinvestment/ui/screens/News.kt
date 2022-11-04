package com.amitnadiger.myinvestment.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController


@Composable
fun News(navController: NavHostController, paddingValues: PaddingValues) {

}

fun getScreenConfig4TAndC():ScreenConfig {
    //Log.e("HomeScreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        topAppBarTitle = "Terms and Conditions", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}