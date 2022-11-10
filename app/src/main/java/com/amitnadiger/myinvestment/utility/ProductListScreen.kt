package com.amitnadiger.myinvestment.utility

import android.util.Log
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.BaseApplication
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import com.amitnadiger.myinvestment.ui.screens.ScreenConfigConst.Companion.DARK_MODE
import com.amitnadiger.myinvestment.ui.screens.ScreenConfigConst.Companion.LIGHT_MODE
import com.amitnadiger.myinvestment.ui.screens.dateFormat
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.IS_DARK_MODE
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.NumberFormat
import java.util.*

private val TAG = "SecDtaStrProvider"
@Composable
fun ProductListScreen(
    navController: NavHostController,
    allProducts: List<Product>,
    padding: PaddingValues,
    parentScreen:String,
) {
    //
    val context = LocalContext.current
    val dataStoreProvider = DataStoreHolder.getDataStoreProvider(
        context,
        DataStoreConst.SECURE_DATASTORE, true
    )
    var nod:String="30"
    runBlocking {
        nod = dataStoreProvider.getString(DataStoreConst.NOTIFICATION_DAYS).first()?:"30"
    }
    var advanceNotifyDays by remember { mutableStateOf(nod.toInt()) }
    Log.e(TAG,"advanceNotifyDays  $advanceNotifyDays and nod = $nod")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        TitleRow(head1 = " Fin Ins\n\n AccNum\n\n Product ",
            head2 = " Dep.amount\n\n Mat.amount\n\n Int.Rte %",
            head3 =" Dep.Date\n\n Mat.Date\n\n Investor")

        LazyColumn(
            Modifier
                .fillMaxWidth()
               // .border(width = 1.dp, color = Color.Black)
        ) {

            items(allProducts) { product ->

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



                var maturityPeriodIsLessThan30Days = false
                var isAlreadyMatured = false
                var color: Color = Color.Unspecified
                //if(isDarkMode) color = Color.White

                val numberOfDays =
                    DateUtility.getNumberOfDaysBetweenTwoDays(product.maturityDate, Calendar.getInstance())

                if (numberOfDays <= 0) {
                    color = Color.Red
                } else if (numberOfDays <= advanceNotifyDays) {
                    maturityPeriodIsLessThan30Days
                    color = Color.Magenta
                }

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
}
