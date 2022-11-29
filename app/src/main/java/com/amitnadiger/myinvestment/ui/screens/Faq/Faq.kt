package com.amitnadiger.myinvestment.ui.screens.Faq

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.R
import com.amitnadiger.myinvestment.ui.NavRoutes

import com.amitnadiger.myinvestment.ui.screens.ScreenConfig

@Composable
fun Faq(navController: NavHostController, paddingValues: PaddingValues) {
    val background =   android.R.color.transparent
    var listOfQuestions = gerFaqList()
    var isGlobalShowAnswerEnabled by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    isGlobalShowAnswerEnabled =!isGlobalShowAnswerEnabled
                })
                //.height(45.dp)
                .background(colorResource(id = background))
             .padding(10.dp)
            // .border(width = 1.dp, color = Color.LightGray)
        ) {
            if(isGlobalShowAnswerEnabled) {
                Text(
                    text = "--",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier,
                    color = Color.Green
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_faq),
                    contentDescription = "faq",
                    colorFilter = ColorFilter.tint(Color.Red),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp))
            }
        }

        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                // .fillMaxWidth()
                .padding(start = 10.dp,end = 10.dp)
        ) {

            items(listOfQuestions) { item ->
                var isIndidualShowAnswerEnabled by remember { mutableStateOf(false) }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            isIndidualShowAnswerEnabled = !isIndidualShowAnswerEnabled
                        })
                        //.height(45.dp)
                        .background(colorResource(id = background))
                ) {
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = item.first,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                        //    .weight(0.2f)
                        // color = Color.Black
                    )

                    /*
                    Text(
                        text = localSign,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                       //   .weight(0.5f)
                        // color = Color.Black
                    )
                     */
                }
                if(isGlobalShowAnswerEnabled ) {
                    Text(
                        text = item.second,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )
                } else {
                    if(isIndidualShowAnswerEnabled) {
                        Text(
                            text = item.second,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

fun getScreenConfig4Faq(): ScreenConfig {
    //Log.e("HomeScreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        screenOnBackPress = NavRoutes.Home.route,
        topAppBarTitle = "FAQ", bottomAppBarTitle = "",
        fabString = "",
        fabColor = Color.Red
    )
}