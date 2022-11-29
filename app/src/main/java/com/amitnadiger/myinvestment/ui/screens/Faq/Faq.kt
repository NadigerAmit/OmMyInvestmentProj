package com.amitnadiger.myinvestment.ui.screens.Faq

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.ui.NavRoutes

import com.amitnadiger.myinvestment.ui.screens.ScreenConfig

@Composable
fun Faq(navController: NavHostController, paddingValues: PaddingValues) {
    val background =   android.R.color.transparent
    val listOfQuestions = gerFaqList()
    var isExpanderEnabled by remember { mutableStateOf(false) }
    var previousExpanderEnabled:Boolean = false

    var numberOfElements = listOfQuestions.size
    /*
    sign = if(isExpanderEnabled ) {
        "-"
    } else {
        "+"
    }

     */
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    ) {
        /*
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    isExpanderEnabled =!isExpanderEnabled
                })
                //.height(45.dp)
                .background(colorResource(id = background))
             .padding(10.dp)
            // .border(width = 1.dp, color = Color.LightGray)
        ) {
            Text(
                text = "Expand All",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .weight(0.2f)
                // color = Color.Black
            )
            Text(
                text = sign,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                //   .weight(0.5f)
                // color = Color.Black
            )

        }

         */
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                // .fillMaxWidth()
                .padding(start = 10.dp,end = 10.dp)
        ) {

            items(listOfQuestions) { item ->
                var sign by remember { mutableStateOf("+") }
                var isShowAnswerEnabled by remember { mutableStateOf(false) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            isShowAnswerEnabled = !isShowAnswerEnabled
                            sign = if(isShowAnswerEnabled ) {
                                "-"
                            } else {
                                "+"
                            }
                        })
                        .height(45.dp)
                        .background(colorResource(id = background))
                ) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = item.first,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .weight(0.2f)
                        // color = Color.Black
                    )
                    Text(
                        text = sign,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                       //   .weight(0.5f)
                        // color = Color.Black
                    )
                }
                if(isShowAnswerEnabled) {
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

fun getScreenConfig4Faq(): ScreenConfig {
    //Log.e("HomeScreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        screenOnBackPress = NavRoutes.Home.route,
        topAppBarTitle = "Faq", bottomAppBarTitle = "",
        fabString = "",
        fabColor = Color.Red
    )
}