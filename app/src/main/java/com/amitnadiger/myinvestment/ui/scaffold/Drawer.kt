package com.amitnadiger.myinvestment.ui.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.DrawerValue
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig
import com.amitnadiger.myinvestment.ui.theme.Purple700
import kotlinx.coroutines.runBlocking


@Composable
fun Drawer(screenConfig: ScreenConfig,navController: NavHostController) {
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

                    ClickableText(
                        text = AnnotatedString(item),
                        modifier = Modifier
                            .padding(20.dp),
                        onClick = {
                            navController.navigate(NavRoutes.History.route)
                            runBlocking {
                                DrawerValue.Closed
                            }
                        },
                        style = TextStyle(
                            fontSize = 14.sp,
                            //fontFamily = FontFamily.Default,
                            //textDecoration = TextDecoration.Underline,
                            color = Purple700
                        )
                    )
                }
            }
        }
    }
}