package com.amitnadiger.myinvestment.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amitnadiger.myinvestment.R
import com.amitnadiger.myinvestment.utility.TitleRow
import com.amitnadiger.myinvestment.utility.nod

@Composable
fun HomeDisplaySettingFragment(displayItemList:List<String>) {

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp,end = 6.dp)

    ){
        items(displayItemList) { item ->
            when(item) {
                "statementTextForHome" -> {
                    Text(
                        text = "\nInvestments are displayed Here.",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                        //color = Color.Black
                    )
                }
                "statementTextForHistory" -> {
                    Text(
                        text = "\nDeletedRecords are displayed Here ." +
                                "\n\nWhen records are deleted from home screen , they will appear in this screen ",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Medium
                        //color = Color.Black
                    )
                }
                "statementText1"->{
                    Text(
                        text = "\nInvestmentsRecords displayed" +
                                " in 3 colours",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light
                        //color = Color.Black
                    )
                }
                "maturedItemsText" ->{
                    //Spacer(modifier = Modifier.width(40.dp))
                    Text(
                        text = "\nRed => Already matured Records ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Red)
                }

                "aboutToMaturedItemsText" -> {
                    Text(
                        text = "\nMagenta => Records with maturityDate <= ${nod.value} days",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Magenta,)
                    Spacer(modifier = Modifier.width(40.dp))
                }

                "normalItemsText"-> {
                    Spacer(modifier = Modifier.width(40.dp))
                    Text(
                        text = "\nBlack/White => Records with maturityDate > ${nod.value} days",
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp,

                        //color = Color.Black
                    )
                }

                "TextLengthLimit"-> {
                    Spacer(modifier = Modifier.width(40.dp))
                    Text(
                        text = "\nFieldLengthLimit => 9 ex: *876543210",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
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
                        fontWeight = FontWeight.Medium
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
                "GuideToAddItems" -> {
                    Text(
                        text = "\nAdd new records by pressing below Red + button  ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                "ImageToAddItems" -> {
                    Spacer(modifier = Modifier.width(30.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                        //.background(colorResource(id = background))
                        // .border(width = 1.dp, color = Color.LightGray)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowDownward,
                            contentDescription = "Down",
                            modifier = Modifier.height(120.dp)
                                .width(80.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                }
                "GuideToDeleteAllItemsFromHistory" -> {
                    Text(
                        text = "\nYou can clear all items from history pressing below Red button  ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                "ImageToDeleteAllItemsFromHistory" -> {
                    Spacer(modifier = Modifier.width(30.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                        //.background(colorResource(id = background))
                        // .border(width = 1.dp, color = Color.LightGray)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowDownward,
                            contentDescription = "Down",
                            modifier = Modifier.height(120.dp)
                                .width(80.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                }
            }
        }
    }
}