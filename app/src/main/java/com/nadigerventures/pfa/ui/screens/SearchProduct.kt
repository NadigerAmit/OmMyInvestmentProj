package com.nadigerventures.pfa.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.room.Product
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.utility.*
import com.nadigerventures.pfa.viewModel.FinProductViewModel
import java.text.NumberFormat
import java.util.*


var searchByFieldValue = mutableStateOf("")
var operationFieldValue = mutableStateOf("")
var valueFieldValue = mutableStateOf("")


data class SearchQuery(val searchByFieldValue: String,
                       val operationFieldValue: String,
                       val valueFieldValue: String)

/*

fun getSearchQueryAsync( searchQueryLocal : Flow<SearchQuery>,dataStorageManager:UnsecureDataStore):SearchQuery? {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    //var TestTrple = dataStorageManager.getSearchDataFromDataStore()
    var query1 :SearchQuery?=null
    coroutineScope.launch  {
        dataStorageManager.getSearchDataFromDataStore().catch {
                exception ->
            if (exception is IOException) {
                emit(Triple("","",""))
            } else {
                throw exception
            }
        }.collect { value ->
            query1 = SearchQuery(value.first,
                value.second,
                value.third)
        }
    }
    return query1
    //return query
}


fun getSearchQuerySync( searchQueryLocal : Flow<SearchQuery>,dataStorageManager:UnsecureDataStore):SearchQuery {
  //  var TestTrple = dataStorageManager.getSearchDataFromDataStore()
    var query :SearchQuery
    var query1 :SearchQuery
    runBlocking {
        /*
        query1 = SearchQuery(TestTrple.first().first,
            TestTrple.first().second,
            TestTrple.first().third)

         */

        query = SearchQuery(searchQueryLocal.first().searchByFieldValue,
            searchQueryLocal.first().operationFieldValue,
            searchQueryLocal.first().valueFieldValue)
    }
    //return query1
    return query
}

 */

@Composable
fun SearchProduct(navController: NavHostController,viewModel: FinProductViewModel,padding:PaddingValues) {


    val allProducts by viewModel.allAccounts.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())
    var searching by rememberSaveable { mutableStateOf(false) }

    var searchByField by rememberSaveable { mutableStateOf("") }
    var operationField by rememberSaveable { mutableStateOf("") }
    var valueField by rememberSaveable { mutableStateOf("") }
    var totalInvestmentAmount = 0
    var totalMaturityAmount = 0.0

    for(i in searchResults) {
        totalInvestmentAmount +=i.investmentAmount
        totalMaturityAmount +=i.maturityAmount
    }
/*
    val dataStorageManager = UnsecureDataStore.getLocalDataStoreManagerInstance()

    val getSearchQuery = getSearchQuerySync(dataStorageManager.searchQueryLocal,dataStorageManager)
    if(getSearchQuery !=null) {
        Log.e("SearchProduct","getSearchQuery.searchByFieldValue = ${getSearchQuery.searchByFieldValue}")
        Log.e("SearchProduct","getSearchQuery.operationFieldValue = ${getSearchQuery.operationFieldValue}")
        Log.e("SearchProduct","getSearchQuery.valueFieldValue = ${getSearchQuery.valueFieldValue}")
    } else {
        Log.e("SearchProduct","getSearchQuery is null")
    }
 */
    var finalList:MutableList<Product> = searchResults.toMutableList()
    finalList.add(
        Product("00000000000",
    "","","TotalInvestmentAmount",0, Calendar.getInstance(), Calendar.getInstance(),1.1,
            0.0f,0)
    )

    // Create a list of cities
    val searchFieldList = listOf("Account Number",
        "Financial Institution Name",
        "Product Type", "Investor Name",
        "Investment Amount", "Investment Date",
        "Maturity Date","Maturity Amount",
        "Interest Rate","Deposit Period",
        "Nominee Name"
    )

    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(5.dp)
    ) {

        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            searchByField = DropDownBox(searchFieldList,"Search by",280.dp,searchByFieldValue.value)
            operationField = DropDownBox(getOperationList(searchByField),"Operation",150.dp,operationFieldValue.value)
            valueField = DropDownBox(getValuesList(searchByField,allProducts),"Value",280.dp,valueFieldValue.value)

            searchByFieldValue.value = searchByField
            operationFieldValue.value = operationField
            valueFieldValue.value = valueField
            /*
            saveToDataStore(SearchQuery(searchByField,
                    operationField,
                    valueField),
                dataStorageManager)

             */


            if(finalList.size>1) {
                TitleRow(head1 = " Fin Ins\n\n AccNum\n\n Product ",
                    head2 = " Dep.amount\n\n Mat.amount\n\n Int.Rte %",
                    head3 =" Dep.Date\n\n Mat.Date\n\n Investor")

                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.Black)
                ) {

                    items(finalList) { product ->
                        var color: Color = getProductColor(product,nod.value.toInt())

                        if(product.accountNumber == "00000000000"
                            && totalInvestmentAmount != 0 ) {
                            SummaryRow("\n   Σ InvestAmount\n\n"+
                                    "  Σ MaturityAmount\n\n","\n  "+totalInvestmentAmount.toString()
                                    + "\n\n"+"  "+
                                    NumberFormat.getInstance().format(totalMaturityAmount)+ "\n\n")
                        } else {
                            ProductRow(
                                product.accountNumber,
                                FirstColumn = truncateString(product.financialInstitutionName) + "\n" + truncateString(
                                    product.accountNumber.toString()
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
                                navController, color,"ProductDetail"
                            )
                        }
                    }
                }
            }
        }
    }
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
            Log.e("search", "Called in SearchProduct with Maturity date ")
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
    Log.e("SearchProduct", "getValuesList() fieldName -$fieldName")
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
                Log.e("depositPeriod", i.depositPeriod.toString() +" Days")
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
        "financial Institution Name",
        "Product Type",
        "Investor Name",
        "Nominee Name" -> return listOf("=","!=")
    }
    return operationFieldList
}
fun getScreenConfig4SearchScreen():ScreenConfig {
    Log.e("SearchScreen","getScreenConfig4SearchScreen");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = false,
        screenOnBackPress = NavRoutes.Home.route,
        enableFab = true,
        enableAction = false,
        topAppBarTitle = "SearchInvestment", bottomAppBarTitle = "",
        fabString = "search",
    )
}