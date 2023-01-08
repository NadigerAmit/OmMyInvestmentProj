package com.nadigerventures.pfa.ui.screens

import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.navigation.NavHostController
import com.nadigerventures.pfa.room.Product
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.ui.screens.fragment.ProductListFragment
import com.nadigerventures.pfa.utility.*
import com.nadigerventures.pfa.viewModel.FinProductViewModel
import java.text.NumberFormat
import java.util.*


var searchByFieldValue = mutableStateOf("")
var operationFieldValue = mutableStateOf("")
var valueFieldValue = mutableStateOf("")
var isSearchQueryUpdated = mutableStateOf(false)
private val TAG = "SearchProduct"

@Composable
fun SearchProduct(navController: NavHostController,viewModel: FinProductViewModel,padding:PaddingValues) {


    val allProducts by viewModel.allAccounts.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())
    var totalInvestmentAmount = 0.0
    var totalMaturityAmount = 0.0
    if(searchResults != null) {
        for(i in searchResults) {
            totalInvestmentAmount +=i.investmentAmount
            totalMaturityAmount +=i.maturityAmount
        }
    }

    if(searchByFieldValue.value != "" &&
        operationFieldValue.value != "" &&
        valueFieldValue.value != "" &&
        isSearchQueryUpdated.value
    ) {
        //Log.i(TAG,"Search triggerred again ")
        search(viewModel)
        isSearchQueryUpdated.value = false
    }
    val searchFieldList = listOf("Account Number",
        "Financial Institution Name",
        "Product Type", "Investor Name",
        "Investment Amount", "Investment Date",
        "Maturity Date","Maturity Amount",
        "Interest Rate",
        "Nominee Name","Deposit Period"
    )

    ProductListFragment(navController,allProducts,searchResults,
        totalInvestmentAmount,totalMaturityAmount,
        padding,"SearchProduct",searchFieldList,)
}

fun search(viewModel: FinProductViewModel) {
    when(searchByFieldValue.value) {
        "Account Number"->{
            viewModel.findProductsBasedOnAccountNumber(valueFieldValue.value,
                operationFieldValue.value)
        }
        "Financial Institution Name"->{
            viewModel.findProductsBasedOnFinanceInstituteName(valueFieldValue.value,
                operationFieldValue.value)
        }
        "Product Type"->{
            viewModel.findProductsBasedOnProductType(valueFieldValue.value,
                operationFieldValue.value)
        }
        "Investor Name"->{
            viewModel.findProductsBasedOnInvestorName(valueFieldValue.value,
                operationFieldValue.value)
        }
        "Investment Amount"->{
            viewModel.findProductsHavingInvestmentAmount(valueFieldValue.value,
                operationFieldValue.value)
        }
        "Investment Date"->{
            viewModel.findProductsHavingInvestmentDate(valueFieldValue.value,
                operationFieldValue.value)
        }
        "Maturity Date"->{
            //Log.i("search", "Called in SearchProduct with Maturity date ")
            viewModel.findProductsHavingMaturityDate(valueFieldValue.value,
                operationFieldValue.value)
        }
        "Maturity Amount"->{
            viewModel.findProductsHavingMaturityAmount(valueFieldValue.value,
                operationFieldValue.value)

        }
        "Interest Rate"->{
            viewModel.findProductsHavingInterestRate(valueFieldValue.value,
                operationFieldValue.value)
        }
        "Deposit Period"->{
            viewModel.findProductsHavingDepositPeriod(valueFieldValue.value,
                operationFieldValue.value)
        }
        "Nominee Name" -> {
            viewModel.findProductsBasedOnNomineeName(valueFieldValue.value,operationFieldValue.value)
        }
        else -> {

        }
    }
}

fun getValuesList(fieldName:String,allProducts: List<Product>,):List<String> {
   // Log.i("SearchProduct", "getValuesList() fieldName -$fieldName")
    var listOfValues = mutableSetOf<String>()
    when(fieldName) {
        "Account Number"->{
            for(i in allProducts) {
                listOfValues.add(i.accountNumber)
            }
        }
        "Financial Institution Name"->{
            for(i in allProducts) {
                listOfValues.add(i.financialInstitutionName)
            }
        }
        "Product Type"->{
            for(i in allProducts) {
                listOfValues.add(i.productType)
            }
        }
        "Investor Name"->{
            for(i in allProducts) {
                listOfValues.add(i.investorName)
            }
        }
        "Investment Amount"->{
            for(i in allProducts) {
                listOfValues.add(i.investmentAmount.toString())
            }
        }
        "Investment Date"->{
            for(i in allProducts) {
                listOfValues.add(DateUtility.getPickedDateAsString(
                    i.investmentDate.get(Calendar.YEAR),
                    i.investmentDate.get(Calendar.MONTH),
                    i.investmentDate.get(Calendar.DAY_OF_MONTH), dateFormat))
            }
        }
        "Maturity Date"->{
            for(i in allProducts) {
                listOfValues.add(DateUtility.getPickedDateAsString(
                    i.maturityDate.get(Calendar.YEAR),
                    i.maturityDate.get(Calendar.MONTH),
                    i.maturityDate.get(Calendar.DAY_OF_MONTH), dateFormat))
            }
        }
        "Maturity Amount"->{
            for(i in allProducts) {
                listOfValues.add(NumberFormat.getInstance().format(i.maturityAmount))
            }
        }
        "Interest Rate"->{
            for(i in allProducts) {
                listOfValues.add(i.interestRate.toString())
            }
        }
        "Nominee Name"->{
            for(i in allProducts) {
                listOfValues.add(i.nomineeName)
            }
        }
        "Deposit Period"->{
            for(i in allProducts) {
                listOfValues.add(i.depositPeriod.toString() +" Days")
                //Log.i("depositPeriod", i.depositPeriod.toString() +" Days")
            }
        }
    }
    return listOfValues.toList()
}

fun getOperationList(searchFieldList:String):List<String> {
    val operationFieldList = listOf("=", "!=",
        ">=",
        ">", "<",
        "<="
    )
    when(searchFieldList) {
        "Account Number",
        "Financial Institution Name",
        "Product Type",
        "Investor Name",
        "Nominee Name" -> return listOf("=","!=")
    }
    return operationFieldList
}
fun getScreenConfig4SearchScreen():ScreenConfig {
    //Log.i("SearchScreen","getScreenConfig4SearchScreen");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        screenOnBackPress = NavRoutes.Home.route,
        enableFab = false,
        enableAction = false,
        enableFilter = false,
        enableSort= false,
        topAppBarTitle = "Filter Investments", bottomAppBarTitle = "",
        fabString = "Filter Investment",
    )
}