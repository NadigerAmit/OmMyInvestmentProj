package com.amitnadiger.myinvestment.ui.screens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Left
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.R
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig
import com.amitnadiger.myinvestment.utility.CustomTextField

@Composable
fun DisplaySetting(navController: NavHostController,
                   padding: PaddingValues
) {
    var numberOfDays by remember { mutableStateOf("30") }
    val startDp: Dp = 0.dp

    val onNumOfDaysTextChange = { text : String ->
        numberOfDays = text
    }
    val background =   android.R.color.transparent

    val displayItemList = listOf(
        "DisplayImage",
        "statementText1",
        "maturedItemsText",
        "aboutToMaturedItemsText",
        "normalItemsText",
        "inputItems",

    )

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp,end = 6.dp)

    ){
        items(displayItemList) { item ->
            when(item) {
                "DisplayImage" ->{
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            //.background(colorResource(id = background))
                        // .border(width = 1.dp, color = Color.LightGray)
                    ){
                        Box(
                            modifier = Modifier.size(110.dp).clip(CircleShape).background(Color.White)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_displaysetting),
                                contentDescription = "DisplaySetting",
                                colorFilter = ColorFilter.tint(Color.Black),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(100.dp)
                            )
                        }

                    }
                    Spacer(modifier = Modifier.width(30.dp))
                }
                "statementText1"->{
                    Text(
                        text = "\nRecords in Home & History Screens \nare displayed" +
                                " in 3 colours",
                        fontSize = 20.sp,
                        //color = Color.Black
                    )
                }
                "maturedItemsText" ->{
                    //Spacer(modifier = Modifier.width(40.dp))
                    Text(
                        text = "\nRed => Already matured Records ",
                        fontSize = 20.sp,
                        color = Color.Red)
                    //Spacer(modifier = Modifier.width(40.dp))
                }
                "aboutToMaturedItemsText" -> {
                    Text(
                        text = "\nMagenta => Records with maturityDate <= 30 days",
                        fontSize = 20.sp,
                        color = Color.Magenta,)
                    Spacer(modifier = Modifier.width(40.dp))
                }

                "normalItemsText"-> {
                    Spacer(modifier = Modifier.width(40.dp))
                    Text(
                        text = "\nBlack/White => Records with maturityDate > 30 days",
                        fontSize = 20.sp,
                        //color = Color.Black
                    )
                }
            }
        }
    }
    /*
   Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
        // .border(width = 1.dp, color = Color.LightGray)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(colorResource(id = background))
            // .border(width = 1.dp, color = Color.LightGray)
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_displaysetting),
                contentDescription = "DisplaySetting",
                colorFilter = ColorFilter.tint(Color.Black),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
        }
       Spacer(modifier = Modifier.width(30.dp))


        Spacer(modifier = Modifier.width(30.dp))
        Text(
            text = "\nRecords in Home & History Screens are displayed" +
                    " in 3 colours",
            fontSize = 20.sp,
            color = Color.Black)
        Spacer(modifier = Modifier.width(40.dp))
        Text(
            text = "\nRed => Already matured Records",
            fontSize = 20.sp,
            color = Color.Red)
        Spacer(modifier = Modifier.width(40.dp))
        Text(
            text = "\nMagenta => Records with maturityDate <= 30 days(by Default) from now",
            fontSize = 20.sp,
            color = Color.Magenta)
        Spacer(modifier = Modifier.width(40.dp))
        Text(
            text = "\nBlack => Records with maturityDate > 30(by Default) days from now",
            fontSize = 20.sp,
            color = Color.Black)

    }
  */
}



fun getScreenConfig4DisplaySetting(): ScreenConfig {
    //Log.e("HomeScreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = false,
        enableDrawer = true,
        enableFab = false,
        topAppBarTitle = "DisplaySettings", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )

}