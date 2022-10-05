package com.amitnadiger.myinvestment.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.UiThread
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.utility.DateUtility
import com.amitnadiger.myinvestment.utility.DateUtility.Companion.getDateInCalendar
import com.amitnadiger.myinvestment.utility.DateUtility.Companion.getPeriodBetween2Dates
import com.amitnadiger.myinvestment.utility.DateUtility.Companion.localDateToCalendar
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*

@Composable
@UiThread
fun ProductDetail(navController: NavHostController,viewModel: FinProductViewModel,accountNumber:String) {

    Log.e("Home","Jai shree Ram Row is clicked ,ProductDetail  AccountNumber = $accountNumber ")
    screenSetUpInProductDetail(viewModel,accountNumber)
}

val dateFormat = "yyyy-MM-dd"

private fun getListOPropertiesOfProduct(productDetail: Product): List<Pair<String, String>> {
    val mutableList = mutableListOf(
        Pair("Investor name", productDetail.investorName),
        Pair("Account number ", productDetail.accountNumber.toString()),
        Pair("Financial institution", productDetail.financialInstitutionName),
        Pair("Product type", productDetail.productType),
        Pair("Investment start date", DateUtility.getPickedDateAsString(
            productDetail.investmentDate.get(Calendar.YEAR),
            productDetail.investmentDate.get(Calendar.MONTH),
            productDetail.investmentDate.get(Calendar.DAY_OF_MONTH)
            ,dateFormat)),
        Pair("Investment amount",  productDetail.investmentAmount.toString()),
        Pair("Maturity date", DateUtility.getPickedDateAsString(
            productDetail.maturityDate.get(Calendar.YEAR),
            productDetail.maturityDate.get(Calendar.MONTH),
            productDetail.maturityDate.get(Calendar.DAY_OF_MONTH)
            ,dateFormat)),
        Pair("Maturity amount", productDetail.maturityAmount.toString()),
        Pair("Interest rate", productDetail.interestRate.toString()),

        Pair("Nominee name", productDetail.nomineeName)
    )
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
    {
        mutableList.add(9,Pair("Investment Duration", getPeriodBetween2Dates(productDetail.investmentDate,productDetail.maturityDate)))
        mutableList.add(10,Pair("Remaining duration",getPeriodBetween2Dates(localDateToCalendar(LocalDateTime.now()),productDetail.maturityDate)))
    } else {
        mutableList.add(9,Pair("Investment Duration",
            DateUtility.getNumberOfDaysBetweenTwoDays(productDetail.maturityDate,productDetail.investmentDate).toString()+" Days"))
        mutableList.add(10,Pair("Remaining duration",
            DateUtility.getNumberOfDaysBetweenTwoDays(productDetail.maturityDate,Calendar.getInstance()).toString()+" Days"))
    }
    return mutableList
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
@UiThread
fun screenSetUpInProductDetail(viewModel: FinProductViewModel,accountNumber:String)  {
    GlobalScope.launch {
        viewModel.findProductsBasedOnAccountNumber(accountNumber)
    }

    var productDetail:Product? = null
    if(viewModel.searchResults.value == null || viewModel.searchResults.value!!.isEmpty()) {
        Log.e("ProductDetail.Kt","Product List is empty returning from screenSetUpInProductDetail !!")
        productDetail =viewModel.findProductBasedOnAccountNumberFromLocalCache(accountNumber)
    } else {
        Log.e("ProductDetail.Kt","Got it from Database ")
        productDetail = viewModel.searchResults.value?.get(0)
    }

   // Log.e("ProductDetail.Kt","Product is retived and list size = ${viewModel.searchResults.value!!.size}")

/*
    Log.e("ProductDetail.Kt","Product is retived in Home \n accountNumber = ${productDetail?.accountNumber}" +
            " \n financialInstitutionName +  ${productDetail?.financialInstitutionName}" +
            " \n investorName = ${productDetail?.investorName} " +
            " \ninvestmentAmount = ${productDetail?.investmentAmount}" +
            " \ninvestmentDate = ${productDetail?.investmentDate}  " +
            " \n maturityDate = ${productDetail?.maturityDate}  " +
            " \n interestRate = $ {productDetail?.interestRate}  " +
            " \n depositPeriod = ${productDetail?.depositPeriod} " +
            " \n nomineeName = ${productDetail?.nomineeName} ")
 */
    val productFieldList = getListOPropertiesOfProduct(productDetail!!)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        //TitleRowProductDetails("\n  Account Details\n")
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Black)
                .padding(3.dp)

        ) {
            items(productFieldList) { productFeild ->
                ProductDetailsRow(productFeild.first,productFeild.second)
            }
        }
    }
}

fun getScreenConfig4ProductDetail():ScreenConfig {
    return ScreenConfig(true,
        true,
        true,
        false,
        "Account Detail","",
        "",
    )
}

@Composable
fun TitleRowProductDetails(head: String)  {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(5.dp)


    ) {
        Text(head, color = Color.White,
            modifier = Modifier
                .weight(0.2f).background(MaterialTheme.colors.primary))
    }
}

@Composable
fun ProductDetailsRow(FirstColumn: String,
               SecondColumn: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        Text(FirstColumn.toString(), modifier = Modifier
            .weight(0.2f))
        Text(SecondColumn , modifier = Modifier.weight(0.2f))
    }
}