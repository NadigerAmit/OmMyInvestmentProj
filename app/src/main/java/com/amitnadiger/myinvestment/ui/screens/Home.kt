package com.amitnadiger.myinvestment.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.utility.DateUtility
import com.amitnadiger.myinvestment.utility.DateUtility.Companion.getDateInCalendar
import com.amitnadiger.myinvestment.utility.DateUtility.Companion.getNumberOfDaysBetweenTwoDays
import com.amitnadiger.myinvestment.utility.ProductRow
import com.amitnadiger.myinvestment.utility.TitleRow
import com.amitnadiger.myinvestment.utility.truncateString
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import java.util.*


@Composable
fun Home(navController: NavHostController,viewModel: FinProductViewModel,padding:PaddingValues) {
    val allProducts by viewModel.allAccounts.observeAsState(listOf())
    //val searchResults by viewModel.searchResults.observeAsState(listOf())
      MainScreen(
          navController,
        allProducts = allProducts,
       // searchResults = searchResults,
        viewModel = viewModel,
          padding)

}

fun getScreenConfig4Home():ScreenConfig {
    Log.e("HomeScvreen","getScreenConfig4Home");
    return ScreenConfig(true,
        true,
        true,
        true,
        "My-Investments","",
        "add",
    )
}

@Composable
fun MainScreen(
    navController: NavHostController,
    allProducts: List<Product>,
   // searchResults: List<Product>,
    viewModel: FinProductViewModel,
    padding:PaddingValues
) {
    //
    //var searching by remember { mutableStateOf(false) }

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
                .border(width = 1.dp, color = Color.Black)
        ) {

            items(allProducts) { product ->

                Log.e("Home.Kt","Product is retived in Home \n accountNumber = ${product.accountNumber}" +
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
                var color: Color = Color.Black
                val numberOfDays =
                    getNumberOfDaysBetweenTwoDays(product.maturityDate, Calendar.getInstance())

                if (numberOfDays < 0) {
                    color = Color.Red
                } else if (numberOfDays <= 30) {
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
                        product.maturityAmount.toString()
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
                    navController, color
                )
            }
        }

    }
}
