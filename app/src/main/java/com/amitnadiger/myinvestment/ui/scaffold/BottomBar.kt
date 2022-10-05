package com.amitnadiger.myinvestment.ui.scaffold

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig

@Composable
fun BottomBar(screenConfig:ScreenConfig) {
    // BottomAppBar Composable
    if(screenConfig.enableBottomAppBar) {
        BottomAppBar(
            cutoutShape = MaterialTheme.shapes.small.copy(
                CornerSize(percent = 50)
            ),
        //    backgroundColor = Color.Unspecified,
            content = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Exit")
            }
        ) /* {
            val selectedIndex = remember { mutableStateOf(0) }

            BottomNavigation(elevation = 10.dp) {

                BottomNavigationItem(icon = {
                    Icon(imageVector = Icons.Default.ArrowBack,"")
                },
                    label = { Text(text = "Exit") },
                    selected = (selectedIndex.value == 0),
                    onClick = {
                        selectedIndex.value = 0
                    })

                BottomNavigationItem(icon = {
                    Icon(imageVector = Icons.Default.Favorite,"")
                },
                    label = { Text(text = "Favorite") },
                    selected = (selectedIndex.value == 1),
                    onClick = {
                        selectedIndex.value = 1
                    })

                BottomNavigationItem(icon = {
                    Icon(imageVector = Icons.Default.ThumbUp,"")
                },
                    label = { Text(text = "Profile") },
                    selected = (selectedIndex.value == 2),
                    onClick = {
                        selectedIndex.value = 2
                    }
                    )
            }
        }
        */
    }

}