package com.nadigerventures.pfa.ui.scaffold

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadigerventures.pfa.ui.screens.ScreenConfig

@Composable
fun TopBar(screenConfig: ScreenConfig,
           onMenuClicked: () -> Unit,
           onSearchClicked: () -> Unit,
           onSortClicked: () -> Unit) {
    if(screenConfig.enableTopAppBar) {
        TopAppBar(
            // Provide Title

            title = {
                Text(text = screenConfig.topAppBarTitle, color = MaterialTheme.colors.primary,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Start,
                //modifier = Modifier.padding(start = screenConfig.topAppBarStartPadding,)
                        modifier =Modifier.fillMaxWidth()
            )},
            elevation = AppBarDefaults.TopAppBarElevation,
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
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort",

                        // When clicked trigger onClick
                        // Callback to trigger drawer open
                        modifier = Modifier.
                        clickable(onClick = onSortClicked)
                            .wrapContentWidth(Alignment.Start),
                        tint = MaterialTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.width(7.dp))
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",

                        // When clicked trigger onClick
                        // Callback to trigger drawer open
                        modifier = Modifier.
                        clickable(onClick = onSearchClicked)
                            .wrapContentWidth(Alignment.Start),
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.width(7.dp))

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
