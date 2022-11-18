package com.amitnadiger.myinvestment.ui.screens

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amitnadiger.myinvestment.ui.NavRoutes

data class ScreenConfig(
    val enableTopAppBar:Boolean,
    val enableBottomAppBar:Boolean,
    val enableDrawer:Boolean,
    val enableFab:Boolean,
    val enableAction:Boolean = false,
    val topAppBarTitle:String,
    val screenOnBackPress:String = NavRoutes.Home.route,
    val topAppBarStartPadding: Dp = 20.dp,
    val bottomAppBarTitle:String,
    val fabString:String,
    val fabColor: Color = Color.Red) {
}