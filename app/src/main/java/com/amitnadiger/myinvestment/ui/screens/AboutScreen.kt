package com.amitnadiger.myinvestment.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.R
import com.amitnadiger.myinvestment.base.appVersion
import com.amitnadiger.myinvestment.ui.NavRoutes


@Composable
fun AboutScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val displayItemList = listOf(
        //  "DisplayImage",
        "version",
        "developer"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                //.height(80.dp)
            //.background(colorResource(id = background))
            // .border(width = 1.dp, color = Color.LightGray)
        ) {
            //Box(
            //    modifier = Modifier.size(230.dp).clip(CircleShape).background(Color.Cyan)
            //) {
                Image(
                    painter = painterResource(id = R.drawable.ic_app_logo),
                    contentDescription = "About",
                   // colorFilter = ColorFilter.tint(Color.Cyan),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                )
            //}
        }
        Spacer(modifier = Modifier.width(30.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp,end = 6.dp)

        ){
            items(displayItemList) { item ->
                when(item) {
                    "version" -> {
                        val version = context.appVersion()
                        Text(
                            text = "\nVersion $version",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium
                            //color = Color.Black
                        )
                    }
                    "developer" -> {
                        Text(
                            text = "\nnadigerventures@gmail.com",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium
                            //color = Color.Black
                        )
                    }
                }
            }
        }

    }
}

fun getScreenConfig4About():ScreenConfig {
    //Log.e("HomeScreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        screenOnBackPress = NavRoutes.Setting.route,
        topAppBarTitle = "About", bottomAppBarTitle = "",
        fabString = "",
        fabColor = Color.Red
    )
}