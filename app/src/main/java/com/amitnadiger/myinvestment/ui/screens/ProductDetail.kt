package com.amitnadiger.myinvestment.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController

import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.utility.DateUtility
import com.amitnadiger.myinvestment.utility.DateUtility.Companion.getPeriodBetween2Dates
import com.amitnadiger.myinvestment.utility.DateUtility.Companion.localDateToCalendar
import com.amitnadiger.myinvestment.viewModel.FinHistoryViewModel
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.*

var showDeleteAlertDialogGlobal =  mutableStateOf(false)
var deletedRecord =  mutableStateOf(Product("",
    "","","",0, Calendar.getInstance(), Calendar.getInstance(),1.1,
    0.0f,0))
private val TAG = "ProductDetail"
val dateFormat = "yyyy-MM-dd"

@Composable
@UiThread
fun ProductDetail(navController: NavHostController,
                  productViewModel: FinProductViewModel,
                  finHistoryViewModel: FinHistoryViewModel,
                  accountNumber:String,padding: PaddingValues) {

    Log.e(TAG,"Jai shree Ram Row is clicked ,ProductDetail  AccountNumber = $accountNumber ")

    deleteRecord(showDialog =showDeleteAlertDialogGlobal.value,
        onDismiss ={},// {showDeleteAlertDialogGlobal.value = false },
        productViewModel,finHistoryViewModel,accountNumber,navController)
    if(deletedRecord.value.accountNumber == accountNumber) {
        addRecordToHistory(finHistoryViewModel)
    }
    screenSetUpInProductDetail(productViewModel,accountNumber,navController,padding)
}

private fun addRecordToHistory(finHistoryViewModel: FinHistoryViewModel) {
    Log.e(TAG,"Added below record to History  = ${deletedRecord.value.accountNumber} ")
    printProductDetails(deletedRecord.value)
    finHistoryViewModel.insertFinProduct(deletedRecord.value)
}

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

        Pair("Maturity amount", NumberFormat.getInstance().format(productDetail.maturityAmount)),
        Pair("Interest rate", productDetail.interestRate.toString()),

        Pair("Nominee name", productDetail.nomineeName),
        Pair("DeleteAndEditButton", "")
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
fun screenSetUpInProductDetail(viewModel: FinProductViewModel,
                               accountNumber:String,
                               navController: NavHostController,
                               padding: PaddingValues)  {
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
    if(productDetail == null) return
    val productFieldList = getListOPropertiesOfProduct(productDetail)


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        //TitleRowProductDetails("\n  Account Details\n")
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Black)
                .padding(3.dp)

        ) {
            items(productFieldList) { productFeild ->
                when(productFeild.first) {
                    "DeleteAndEditButton" -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            // .padding(50.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                showDeleteAlertDialogGlobal.value = true
                                Log.e("DeleteAlert","Button clicked showDeleteAlertDialog.value - " +
                                        "${showDeleteAlertDialogGlobal.value}")
                            },modifier = Modifier
                                .padding(10.dp)) {
                                Icon(Icons.Filled.Delete, "Delete")
                                Spacer( modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                Text("Delete")
                            }
                            Button(onClick = {
                                navController.navigate(NavRoutes.AddProduct.route + "/$accountNumber") {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    //restoreState = true
                                }

                            },modifier = Modifier
                                .padding(10.dp)) {
                                Icon(Icons.Filled.Edit, "Edit")
                                Spacer( modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                Text("Edit")
                            }
                        }
                    } else -> {
                        ProductDetailsRow(productFeild.first,productFeild.second)
                    }
                }
            }
        }
    }
}

private fun printProductDetails(productDetail: Product){
    Log.e("ProductDetail.Kt","Product is retived in Home \n accountNumber = ${productDetail?.accountNumber}" +
            " \n financialInstitutionName +  ${productDetail?.financialInstitutionName}" +
            " \n investorName = ${productDetail?.investorName} " +
            " \ninvestmentAmount = ${productDetail?.investmentAmount}" +
            " \ninvestmentDate = ${productDetail?.investmentDate}  " +
            " \n maturityDate = ${productDetail?.maturityDate}  " +
            " \n interestRate = $ {productDetail?.interestRate}  " +
            " \n depositPeriod = ${productDetail?.depositPeriod} " +
            " \n nomineeName = ${productDetail?.nomineeName} ")

}

@Composable
fun deleteRecord(showDialog: Boolean,
                 onDismiss: () -> Unit,
                 productViewModel: FinProductViewModel,
                 finHistoryViewModel: FinHistoryViewModel,
                 accountNumber:String,
                 navController: NavHostController):Product? {
    val context = LocalContext.current
    var rec :Product?= null

    Log.e("DeleteAlert","Called ->showDialog = $showDialog")
    if (showDialog) {

        Log.e("DeleteAlert","Showing the alert dialog  = $showDialog")
        AlertDialog(
            title = { Text(text = "Are you sure to delete this record ?",color = Color.Red) },
            text = { Text(text = "Deleted items will be moved to history , " +
                    "Delete from history if permanent deletion is required")},
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    rec = productViewModel.findProductBasedOnAccountNumberFromLocalCache(accountNumber)
                    if(rec!= null) {
                        productViewModel.deleteFinProduct(accountNumber)
                        Toast.makeText(context, "Record with accNum : $accountNumber deleted" , Toast.LENGTH_LONG)
                            .show()
                        navController.navigate(NavRoutes.Home.route)
                        deletedRecord.value = rec!!
                        showDeleteAlertDialogGlobal.value = false
                    }
                })
                { Text(text = "Continue delete",color = Color.Red) }
            },
            shape  = MaterialTheme.shapes.medium,
            dismissButton = {
                onDismiss()
                TextButton(onClick = {
                    showDeleteAlertDialogGlobal.value = false
                    navController.navigate(NavRoutes.ProductDetail.route + "/$accountNumber")
                })
                { Text(text = "Cancel delete") }
            },
            properties= DialogProperties(dismissOnBackPress = true,
                dismissOnClickOutside=true)
        )
    }
    return rec
}

fun getScreenConfig4ProductDetail():ScreenConfig {
    Log.e("ProductDetail","getScreenConfig4ProductDetail");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        enableAction = false,
        topAppBarTitle = "Account Detail", bottomAppBarTitle = "",
        fabString = "",
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
                .weight(0.2f)
                .background(MaterialTheme.colors.primary))
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