package com.amitnadiger.myinvestment.ui.screens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Left
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.R
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.ScreenConfig
import com.amitnadiger.myinvestment.utility.CustomTextField
import com.amitnadiger.myinvestment.utility.TitleRow
import com.amitnadiger.myinvestment.utility.nod

@Composable
fun DisplaySetting(navController: NavHostController,
                   padding: PaddingValues
) {
    val displayItemList = listOf(
      //  "DisplayImage",
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
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp,end = 6.dp)

        ){
            items(displayItemList) { item ->
                when(item) {
                    "DisplayImage" -> {

                    }
                    "statementText1"->{
                        Text(
                            text = "\nInvestmentsRecords displayed" +
                                    " in 3 colours",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                            //color = Color.Black
                        )
                    }
                    "maturedItemsText" ->{
                        //Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nRed => Already matured Records ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red)
                    }

                    "aboutToMaturedItemsText" -> {
                        Text(
                            text = "\nMagenta => Records with maturityDate <= ${nod.value} days",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Magenta,)
                        Spacer(modifier = Modifier.width(40.dp))
                    }

                    "normalItemsText"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nBlack/White => Records with maturityDate > ${nod.value} days",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,

                            //color = Color.Black
                        )
                    }

                    "TextLengthLimit"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nFieldLengthLimit => 9 ex: *876543210",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                            //color = Color.Black
                        )
                        Text(
                            text = "Max 9 characters are shown from right to left i.e LSB " +
                                    "remaining chars are truncated & represented by one * at the beginning " +
                                    "\nExample: Actual AccountNum = 109876543210 \n" +
                                    "shown as *876543210 <= Here 109 is truncated to *",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }

                    "FieldDisplay"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nMeaning of Fields in Home & History",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                            //color = Color.Black
                        )
                        TitleRow(head1 = " Fin Ins\n\n AccNum\n\n Product ",
                            head2 = " Dep.amount\n\n Mat.amount\n\n Int.Rte %",
                            head3 =" Dep.Date\n\n Mat.Date\n\n Investor")
                    }
                    "FinIns"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nFinIns => Financial Institution where investment can be done such as Banks ,etc",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }
                    "AccNum"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nAccNum => Account number in fin Inst which can be unique",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }
                    "Product"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nProduct => Type of investment such as FD, SB , insurance , NSC,KVP,etc ",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }
                    "Dep.amount"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nDep.amount =>Deposit Amount i.e initial investment  ",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }
                    "Mat.amount"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nMat.amount =>Maturity Amount  ",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }
                    "Int.Rte"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nInt.Rte =>InterestRate usually in %  ",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }
                    "Dep.Date"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nDep.Date =>Deposit or investment Date  ",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }
                    "Mat.Date"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nMat.Date =>Maturity Date  ",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                    }
                    "Investor"-> {
                        Spacer(modifier = Modifier.width(40.dp))
                        Text(
                            text = "\nInvestor => Investor name  ",
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                        Text(
                            text = "\n  ",
                            fontSize = 15.sp,
                            //color = Color.Black
                        )
                        Text(
                            text = "\n  ",
                            fontSize = 15.sp,
                            //color = Color.Black
                        )
                    }
                }
            }
        }
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