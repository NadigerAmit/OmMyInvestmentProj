package com.nadigerventures.pfa.utility

import android.util.Log

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavHostController

import com.nadigerventures.pfa.room.Product
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.ui.screens.HomeDisplaySettingFragment

import com.nadigerventures.pfa.ui.screens.dateFormat

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.NumberFormat
import java.util.*

var nod = mutableStateOf("30")
private val TAG = "SecDtaStrProvider"
@Composable
fun ProductListScreen(
    navController: NavHostController,
    allProducts: List<Product>,
    padding: PaddingValues,
    parentScreen:String,
    displayItemList:List<String>,
    advanceNotifyDays:Int
) {
/*
    val context = LocalContext.current
    val dataStoreProvider = DataStoreHolder.getDataStoreProvider(
        context,
        DataStoreConst.SECURE_DATASTORE, true
    )

    runBlocking {
        nod.value = dataStoreProvider.getString(DataStoreConst.NOTIFICATION_DAYS).first() ?: "30"
    }
    var advanceNotifyDays by remember { mutableStateOf(nod.value.toInt()) }

 */
    Log.e(TAG, "advanceNotifyDays  $advanceNotifyDays and nod = ${nod.value}")
    if (allProducts.isNotEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            TitleRow(
                head1 = " Fin Ins\n\n AccNum\n\n Product ",
                head2 = " Dep.amount\n\n Mat.amount\n\n Int.Rte %",
                head3 = " Dep.Date\n\n Mat.Date\n\n Investor"
            )

            LazyColumn(
                Modifier
                    .fillMaxWidth()
                // .border(width = 1.dp, color = Color.Black)
            ) {

                items(allProducts) { product ->

                    /*
                    Log.e(TAG,"Product is retived in $parentScreen \n accountNumber = ${product.accountNumber}" +
                            // " \n financialInstitutionName +  ${product.financialInstitutionName}" +
                            //  " \n investorName = ${product.investorName} " +
                            //  " \ninvestmentAmount = ${product.investmentAmount}" +
                            //   " \ninvestmentDate in long = ${product.investmentDate.time.time}  " +
                            " \n maturityDate in Long = ${product.maturityDate}  "
                        //    + " \n interestRate = ${product.interestRate}  " +
                        //       " \n depositPeriod = ${product.depositPeriod} " +
                        //       " \n nomineeName = ${product.nomineeName} "
                    )
                     */

                    var color: Color = getProductColor(product, advanceNotifyDays)

                    ProductRow(
                        product.accountNumber,
                        FirstColumn = truncateString(product.financialInstitutionName) + "\n" + truncateString(
                            product.accountNumber
                        )
                                + "\n" + truncateString(product.productType),
                        SecondColumn = truncateString(product.investmentAmount.toString()) + "\n" + truncateString(
                            NumberFormat.getInstance().format(product.maturityAmount)
                        ) + "\n" +
                                truncateString(product.interestRate.toString()),
                        ThirdColumn = truncateString(
                            DateUtility.getPickedDateAsString(
                                product.investmentDate.get(Calendar.YEAR),
                                product.investmentDate.get(Calendar.MONTH),
                                product.investmentDate.get(Calendar.DAY_OF_MONTH), dateFormat
                            )
                        ) + "\n" +
                                truncateString(
                                    DateUtility.getPickedDateAsString(
                                        product.maturityDate.get(Calendar.YEAR),
                                        product.maturityDate.get(Calendar.MONTH),
                                        product.maturityDate.get(Calendar.DAY_OF_MONTH), dateFormat
                                    )
                                ) + "\n" +
                                truncateString(product.investorName),
                        navController, color,
                        parentScreen
                    )
                }
            }
        }
    } else {
        HomeDisplaySettingFragment(displayItemList)
    }
}
