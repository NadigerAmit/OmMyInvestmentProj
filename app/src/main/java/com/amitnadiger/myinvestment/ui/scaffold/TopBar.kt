package com.amitnadiger.myinvestment.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig

@Composable
fun TopBar(screenConfig: ScreenConfig,
           onMenuClicked: () -> Unit,
           onSearchClicked: () -> Unit) {
    if(screenConfig.enableTopAppBar) {
        TopAppBar(
            // Provide Title

            title = {
                Text(text = screenConfig.topAppBarTitle, color = MaterialTheme.colors.primary,
                    fontSize = 30.sp,
                modifier = Modifier.padding(start = screenConfig.topAppBarStartPadding)
            )},
           // backgroundColor = Color(0xFFC0E8D5),
            // Provide the navigation Icon (Icon on the left to toggle drawer)

            navigationIcon = {
                if(screenConfig.enableDrawer){
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",

                        // When clicked trigger onClick
                        // Callback to trigger drawer open
                        modifier = Modifier.clickable(onClick = onMenuClicked),
                        tint = MaterialTheme.colors.primary
                    )
                }
            },

            actions = {

                if(screenConfig.enableDrawer &&
                   screenConfig.enableAction) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",

                        // When clicked trigger onClick
                        // Callback to trigger drawer open
                        modifier = Modifier.
                        clickable(onClick = onSearchClicked)
                            .wrapContentWidth(Alignment.Start),
                        tint = MaterialTheme.colors.primary
                    )
                }
            },
            // background color of topAppBar
            backgroundColor = MaterialTheme.colors.background
        )
    }

}
