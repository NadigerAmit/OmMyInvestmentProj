package com.amitnadiger.myinvestment.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig


@Composable
fun Drawer(screenConfig: ScreenConfig) {
    val drawerItems =
        listOf("Profile", "Settings", "History", "Terms and Conditions", "License", "Tutorial")
    // Column Composable
    if (screenConfig.enableDrawer) {
        Column(
            Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            // Repeat is a loop which
            // takes count as argument
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                   // .border(width = 1.dp, color = Color.Black)
            ) {
                items(drawerItems) { item ->
                    Text(text = "$item", modifier = Modifier.padding(20.dp), color = Color.Black)
                }
            }
        }
    }
}