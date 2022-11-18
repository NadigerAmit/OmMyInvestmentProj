package com.amitnadiger.myinvestment.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.ui.NavRoutes


@Composable
fun Tutorial(navController: NavHostController, paddingValues: PaddingValues) {

}


fun getScreenConfig4Tutorial():ScreenConfig {
    //Log.e("HomeScreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        screenOnBackPress = NavRoutes.Home.route,
        topAppBarTitle = "Tutorial", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}