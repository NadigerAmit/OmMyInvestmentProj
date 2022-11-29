package com.amitnadiger.myinvestment.ui.screens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.R
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig
import com.amitnadiger.myinvestment.ui.screens.HomeDisplaySettingFragment

@Composable
fun DisplaySetting(navController: NavHostController,
                   padding: PaddingValues
) {
    val displayItemList = listOf(
      //  "DisplayImage",
        "statementText0",
        "statementText1",
        "maturedItemsText",
        "aboutToMaturedItemsText",
        "normalItemsText",
        "inputItems",
        "TextLengthLimit",
        "FieldDisplay",
        "FinIns",
        "AccNum",
        "Product",
        "Dep.amount",
        "Mat.amount",
        "Int.Rte",
        "Dep.Date",
        "Mat.Date",
        "Investor"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
            //.background(colorResource(id = background))
            // .border(width = 1.dp, color = Color.LightGray)
        ) {
            Box(
                modifier = Modifier.size(80.dp).clip(CircleShape).background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_displaysetting),
                    contentDescription = "DisplaySetting",
                    colorFilter = ColorFilter.tint(Color.Black),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(30.dp))
        HomeDisplaySettingFragment(displayItemList)
    }
}

fun getScreenConfig4DisplaySetting(): ScreenConfig {
    //Log.e("HomeScreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        screenOnBackPress = NavRoutes.Setting.route,
        topAppBarTitle = "DisplaySettings", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}