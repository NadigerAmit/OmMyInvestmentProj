package com.amitnadiger.myinvestment.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController


@Composable
fun ProfilePage(navController: NavHostController, paddingValues: PaddingValues) {

}

fun getScreenConfig4Profile():ScreenConfig {
    //Log.e("HomeScreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        topAppBarTitle = "Profile", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}