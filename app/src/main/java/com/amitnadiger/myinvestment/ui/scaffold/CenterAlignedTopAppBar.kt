package com.amitnadiger.myinvestment.ui.scaffold

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig


@Composable
fun CenterAlignedTopBar(screenConfig: ScreenConfig,
           onMenuClicked: () -> Unit,
           onSearchClicked: () -> Unit) {
    val appBarHorizontalPadding = 4.dp
    val titleIconModifier = Modifier.fillMaxHeight()
        .width(72.dp - appBarHorizontalPadding)

    TopAppBar(
        //backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier= Modifier.fillMaxWidth()) {

        //TopAppBar Content
        Box(Modifier.height(32.dp)) {

            //Navigation Icon
            Row(
                titleIconModifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    IconButton(
                        onClick = { onMenuClicked },
                        enabled = true,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                        )
                    }
                }
            }

            //Title
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            text = screenConfig.topAppBarTitle
                        )
                    }
                }
            }

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        if(screenConfig.enableDrawer){
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search",

                                // When clicked trigger onClick
                                // Callback to trigger drawer open
                                modifier = Modifier.
                                    // clickable(onClick = onSearchClicked).
                                    wrapContentWidth(Alignment.Start),
                                tint = Color.Black
                            )
                        }
                    }
                )
            }


            /*
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,


                    content =  {
                        if(screenConfig.enableDrawer){
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search",

                                // When clicked trigger onClick
                                // Callback to trigger drawer open
                                modifier = Modifier.
                                clickable(onClick = onSearchClicked)
                                .wrapContentWidth(Alignment.Start),
                                tint = Color.Black
                            )
                        }
                    }
                )
           // }
        }
            */
        }
    }
}