package com.amitnadiger.myinvestment.ui.screens

import androidx.compose.ui.graphics.Color

data class ScreenConfig(
    val enableTopAppBar:Boolean,
    val enableBottomAppBar:Boolean,
    val enableDrawer:Boolean,
    val enableFab:Boolean,
    val topAppBarTitle:String,
    val bottomAppBarTitle:String,
    val fabString:String,
    val fabColor: Color = Color.Red) {
}