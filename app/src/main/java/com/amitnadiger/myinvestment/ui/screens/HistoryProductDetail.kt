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
import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
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
import com.amitnadiger.myinvestment.viewModel.FinHistoryViewModel
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalDateTime
import java.util.*

var showMoveAlertDialogGlobal =  mutableStateOf(false)
var movedRecord =  mutableStateOf(
    Product("",
    "","","",0, Calendar.getInstance(), Calendar.getInstance(),1.1,
    0.0f,0)
)
private val TAG = "HistoryProductDetail"


@Composable
@UiThread
fun HistoryProductDetail(navController: NavHostController,
                  productViewModel: FinProductViewModel,
                  finHistoryViewModel: FinHistoryViewModel,
                  accountNumber:String, padding: PaddingValues
) {

    Log.e(TAG,"Jai shree Ram Row is clicked ,HistoryProductDetail  AccountNumber = $accountNumber ")

    moveRecord(showDialog =showMoveAlertDialogGlobal.value,
        onDismiss ={},// {showDeleteAlertDialogGlobal.value = false },
        productViewModel,finHistoryViewModel,accountNumber,navController)
    if(movedRecord.value.accountNumber == accountNumber) {
        addRecordToHome(productViewModel)
    }
    screenSetUpInHistoryProductDetail(productViewModel,
        finHistoryViewModel,
        accountNumber,
        navController,
        padding)
}

private fun addRecordToHome(productViewModel: FinProductViewModel) {
    Log.e(TAG,"Added below record to History  = ${movedRecord.value.accountNumber} ")
    printProductDetails(movedRecord.value)
    productViewModel.insertFinProduct(movedRecord.value)
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
        Pair("DeleteAndMoveButton", "")
    )
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
    {
        mutableList.add(9,Pair("Investment Duration",
            DateUtility.getPeriodBetween2Dates(
                productDetail.investmentDate,
                productDetail.maturityDate
            )
        ))
        mutableList.add(10,Pair("Remaining duration",
            DateUtility.getPeriodBetween2Dates(
                DateUtility.localDateToCalendar(LocalDateTime.now()),
                productDetail.maturityDate
            )
        ))
    } else {
        mutableList.add(9,Pair("Investment Duration",
            DateUtility.getNumberOfDaysBetweenTwoDays(productDetail.maturityDate,productDetail.investmentDate).toString()+" Days"))
        mutableList.add(10,Pair("Remaining duration",
            DateUtility.getNumberOfDaysBetweenTwoDays(productDetail.maturityDate, Calendar.getInstance()).toString()+" Days"))
    }
    return mutableList
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
@UiThread
fun screenSetUpInHistoryProductDetail(productViewModel: FinProductViewModel,
                               historyViewModel: FinHistoryViewModel,
                               accountNumber:String,
                               navController: NavHostController,
                               padding: PaddingValues
)  {

    var productDetail: Product? = historyViewModel.findProductBasedOnAccountNumberFromLocalCache(accountNumber)
        ?: return

    val productFieldList = getListOPropertiesOfProduct(productDetail!!)


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
                    "DeleteAndMoveButton" -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            // .padding(50.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                showMoveAlertDialogGlobal.value = true
                                Log.e("RestoreAlert","Button clicked showMoveAlertDialogGlobal.value - " +
                                        "${showMoveAlertDialogGlobal.value}")
                            },modifier = Modifier
                                .padding(10.dp)) {
                                //Icon(Icons.Filled.Restore, "Restore")
                                //Spacer( modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                Text("RestoreToHome")
                            }
                            Button(onClick = {
                                historyViewModel.deleteFinProduct(accountNumber)
                                navController.navigate(NavRoutes.History.route)  {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                }
                            },modifier = Modifier
                                .padding(10.dp)) {
                                Icon(Icons.Filled.Delete, "Delete")
                                Spacer( modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                Text("Delete")
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
    Log.e(TAG,"Product is retived in Home \n accountNumber = ${productDetail?.accountNumber}" +
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
fun moveRecord(showDialog: Boolean,
                 onDismiss: () -> Unit,
                 productViewModel: FinProductViewModel,
                 finHistoryViewModel: FinHistoryViewModel,
                 accountNumber:String,
                 navController: NavHostController
): Product? {
    val context = LocalContext.current
    var rec : Product?= null

    Log.e(TAG,"MoveAlert Called ->showDialog = $showDialog")
    if (showDialog) {

        Log.e(TAG,"Showing the alert dialog  = $showDialog")
        AlertDialog(
            title = { Text(text = "Are you sure to move this record to home?",color = Color.Red) },
            text = { Text(text = "Moved items will be moved to Home " )
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    rec = finHistoryViewModel.findProductBasedOnAccountNumberFromLocalCache(accountNumber)
                    if(rec!= null) {
                        finHistoryViewModel.deleteFinProduct(accountNumber)
                        Toast.makeText(context, "Record with accNum : $accountNumber Restored" , Toast.LENGTH_LONG)
                            .show()
                        navController.navigate(NavRoutes.History.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                        }
                        movedRecord.value = rec!!
                    }
                    showMoveAlertDialogGlobal.value = false
                })
                { Text(text = "Continue Restore",color = Color.Red) }
            },
            shape  = MaterialTheme.shapes.medium,
            dismissButton = {
                onDismiss()
                TextButton(onClick = {
                    showMoveAlertDialogGlobal.value = false
                    navController.navigate(NavRoutes.HistoryProductDetail.route + "/$accountNumber") {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                    }
                })
                { Text(text = "Cancel Restore",
                    //modifier = Modifier.padding(end = 40.dp)
                ) }
            },
            properties= DialogProperties(dismissOnBackPress = true,
                dismissOnClickOutside=true)
        )
    }
    return rec
}

fun getScreenConfig4HistoryProductDetail():ScreenConfig {
    Log.e("ProductDetail","getScreenConfig4ProductDetail");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        topAppBarTitle = "HistoryDetail", bottomAppBarTitle = "",
        fabString = "",
    )
}

