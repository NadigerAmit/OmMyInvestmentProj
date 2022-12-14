package com.nadigerventures.pfa.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.room.Product
import com.nadigerventures.pfa.room.ProductUpdate
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.utility.CustomTextField
import com.nadigerventures.pfa.utility.DateUtility
import com.nadigerventures.pfa.utility.DateUtility.Companion.getNumberOfDaysBetweenTwoDays
import com.nadigerventures.pfa.viewModel.FinProductViewModel
import java.util.*

var isRecordInUpdateMode =  mutableStateOf(false)

@Composable
fun AddProduct(navController: NavHostController,viewModel: FinProductViewModel,padding: PaddingValues,
    accountNumber:String = "") {
    //Log.i("AddProductScreen","Adding the product ")
    screenSetUpInAddProductScreen(navController,viewModel,padding,accountNumber)
}

fun getScreenConfig4AddProduct():ScreenConfig {
    //Log.i("AddProduct","getScreenConfig4AddProduct");
    if(!isRecordInUpdateMode.value) {
        return ScreenConfig(
            enableTopAppBar = true,
            enableBottomAppBar = true,
            enableDrawer = true,
            screenOnBackPress = NavRoutes.Home.route,
            enableFab = false,
            topAppBarTitle = "Add Investment", bottomAppBarTitle = "",
            fabString = "",
        )
    }
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = false,
        topAppBarTitle = "Update Investment", bottomAppBarTitle = "",
        fabString = "",
    )
}


@Composable
fun screenSetUpInAddProductScreen(navController: NavHostController,viewModel: FinProductViewModel,
                                  padding:PaddingValues,
                                  accountNumber:String)  {


    var product:Product? = null
    if(accountNumber!= null) {
        product = viewModel.findProductBasedOnAccountNumberFromLocalCache(accountNumber)
    }
    val dateFormat = "yyyy-MM-dd"

    var accountNumber by remember { mutableStateOf(product?.accountNumber?:"") }
    var finInstitutionName by remember { mutableStateOf(product?.financialInstitutionName?:"") }
    var investorName by remember { mutableStateOf(product?.investorName?:"") }
    var investmentAmount by remember { mutableStateOf(product?.investmentAmount?.toString()?:"") }
    var investmentDate by remember { mutableStateOf(product?.investmentDate?:Calendar.getInstance()) }
    var maturityDate by remember { mutableStateOf(product?.maturityDate?:Calendar.getInstance()) }
    var maturityAmount by remember { mutableStateOf(product?.maturityAmount?.toString()?:"") }
    var interestRate by remember { mutableStateOf(product?.interestRate?.toString()?:"") }
    var productType by remember { mutableStateOf(product?.productType?:"") }
    var nomineeName by remember { mutableStateOf(product?.nomineeName?:"") }


    val onAccountNumTextChange = { text : String ->
        accountNumber = text
    }

    val onInvestorNameTextChange = { text : String ->
        investorName = text
    }

    val onFinInstitutionNameTextChange = { text : String ->
        finInstitutionName = text
    }

    val onProductTypeTextChange = { text : String ->
        productType = text
    }

    val onInvestmentAmountTextChange = { text : String ->
        investmentAmount = text
    }

    val onInvestmentDateTextChange = { text : String ->
        //Log.e("onInDChange","investmentDate = $text")
        investmentDate = DateUtility.getCalendar(text,dateFormat)
    }

    val onMaturityDateTextChange = { text : String ->
        maturityDate = DateUtility.getCalendar(text,dateFormat)
    }

    val onMaturityAmountTextChange = { text : String ->
        maturityAmount = text
    }

    val onInterestRateTextChange = { text : String ->
        interestRate = text
    }

    val onNomineeNameTextChange = { text : String ->
        nomineeName = text
    }

    if(product !=null) {
        LaunchedEffect(Unit) {
            isRecordInUpdateMode.value = true
            finInstitutionName = product.financialInstitutionName
            investmentAmount = product.investmentAmount.toString()
            investorName = product.investorName
            investmentDate = product.investmentDate
            maturityDate = product.maturityDate
            maturityAmount = product.maturityAmount.toString()
            interestRate = product.interestRate.toString()
            productType = product.productType
            nomineeName = product.nomineeName
        }
    } else {
        isRecordInUpdateMode.value = false
    }

    val productDetailItemList = listOf(
        Pair("Account Number",Pair(accountNumber,onAccountNumTextChange)),
        Pair("Investment Amount",Pair(investmentAmount,onInvestmentAmountTextChange)),
        Pair("Investor Name",Pair(investorName,onInvestorNameTextChange)),
        Pair("FinInstitution Name",Pair(finInstitutionName,onFinInstitutionNameTextChange)),
        Pair("Product Type",Pair(productType,onProductTypeTextChange)),
        Pair("Investment Date",Pair(DateUtility.getPickedDateAsString(
            investmentDate.get(Calendar.YEAR),
            investmentDate.get(Calendar.MONTH),
            investmentDate.get(Calendar.DAY_OF_MONTH)
            ,dateFormat),onInvestmentDateTextChange)),
        Pair("Maturity Date",Pair(DateUtility.getPickedDateAsString(
            maturityDate.get(Calendar.YEAR),
            maturityDate.get(Calendar.MONTH),
            maturityDate.get(Calendar.DAY_OF_MONTH)
            ,dateFormat),onMaturityDateTextChange)),
        Pair("Maturity Amount",Pair(maturityAmount,onMaturityAmountTextChange)),
        Pair("Interest Rate",Pair(interestRate,onInterestRateTextChange)),
        Pair("Nominee Name",Pair(nomineeName,onNomineeNameTextChange)),
        Pair("Save",Pair("") { _: String -> })
    )

    val context = LocalContext.current

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        items(productDetailItemList) { items ->
            when(items.first) {
                "Save" -> {
                    Button(onClick = {
                        fun validateInputs():Boolean {
                            Log.i("AddProduct","In validateInputs ")
                            if(!validateAccountNumber(accountNumber,context) ||
                                !validateFinInstitution(finInstitutionName,context) ||
                                !validateInvestmentDate(investmentDate,context) ||
                                !validateMaturityDate(investmentDate,maturityDate,context) ||
                                !validateInvestorName(investorName,context) ||
                                !validateInvestmentAmount(investmentAmount,context) ||
                                !validateMaturityAmount(maturityAmount,context) ||
                                !validateInterestRate(interestRate,context)) {
                                return false
                            }
                            return true
                        }
                        if(validateInputs()) {
                            if(!isRecordInUpdateMode.value ) {
                                //Log.i("AddProduct","Inserting the new product  ")
                                viewModel.insertFinProduct(
                                    Product(
                                        accountNumber,
                                        finInstitutionName,
                                        productType,
                                        investorName,
                                        investmentAmount.toDouble(),
                                        investmentDate,
                                        maturityDate,
                                        maturityAmount.toDouble(),
                                        interestRate.toFloat(),
                                        getNumberOfDaysBetweenTwoDays(maturityDate,investmentDate).toInt(),
                                        nomineeName
                                    )
                                )
                            } else {
                                //Log.i("AddProduct","Updating the existing product  ")
                                viewModel.updateFinProduct(
                                    ProductUpdate(
                                        accountNumber,
                                        finInstitutionName,
                                        productType,
                                        investorName,
                                        investmentAmount.toDouble(),
                                        investmentDate,
                                        maturityDate,
                                        maturityAmount.toDouble(),
                                        interestRate.toFloat(),
                                        getNumberOfDaysBetweenTwoDays(maturityDate,investmentDate).toInt(),
                                        nomineeName
                                    )
                                )
                                navController.navigate(NavRoutes.ProductDetail.route + "/$accountNumber") {
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
                                    restoreState = true
                                }
                            }
                            isRecordInUpdateMode.value = false
                            Toast.makeText(context, "Saving to DB", Toast.LENGTH_LONG)
                                .show()
                        }
                    }) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier
                        .width(40.dp)
                        .height(90.dp))
                }
                "Investment Date",
                "Maturity Date"->
                CustomTextField(
                    modifier = Modifier.clickable {
                        DateUtility.showDatePickerDialog(context,items.second.first,dateFormat,items.second.second)
                    },
                    text = items.second.first,
                    placeholder = items.first,
                    onChange = items.second.second,
                    isEnabled = false,
                    imeAction = ImeAction.Next
                )
                "Investment Amount",
                "Maturity Amount",
                "Interest Rate" -> {
                    CustomTextField(
                        modifier = Modifier.clickable {

                        },
                        text = items.second.first,
                        placeholder = items.first,
                        onChange = items.second.second,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                }
                else -> {
                    CustomTextField(
                        placeholder = items.first,
                        text = items.second.first,
                        onChange = items.second.second,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                }
            }
        }
    }
}

private fun validateAccountNumber(accountNumber: String,context:Context):Boolean {
    if(accountNumber.isEmpty()
        ||accountNumber.isBlank()){
        Toast.makeText(context, "Cant have a blank AccountNumber field ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}

private fun validateFinInstitution(finInstitutionName: String,context:Context):Boolean {
    if(finInstitutionName.isEmpty()||finInstitutionName.isBlank()) {
        Toast.makeText(context, "Cant have a blank FinInstitutionName field ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}

private fun validateProduct(product: String,context:Context):Boolean {
    if(product.isEmpty()||product.isBlank()) {
        Toast.makeText(context, "Cant have a blank Product field ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}


private fun validateInvestorName(investorName: String,context:Context):Boolean {
    if(investorName.isEmpty()||investorName.isBlank()) {
        Toast.makeText(context, "Cant have a blank investorName field ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}

private fun validateInvestmentAmount(investmentAmount: String,context:Context):Boolean {
    if(investmentAmount.isEmpty()||investmentAmount.isBlank()) {
        Toast.makeText(context, "Cant have a blank investmentAmount field ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    try {
        investmentAmount.toDouble()
    } catch (ex: NumberFormatException) {
        // Not a float
        Toast.makeText(context, "Cant have a non digit field in Investment Amount ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}

private fun validateMaturityAmount(maturityAmount: String,context:Context):Boolean {
    if(maturityAmount.isEmpty()||maturityAmount.isBlank()) {
        Toast.makeText(context, "Cant have a blank maturityAmount field ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    try {
        maturityAmount.toDouble()
    } catch (ex: NumberFormatException) {
        // Not a float
        Toast.makeText(context, "Cant have a non digit field in maturity Amount ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}

private fun validateInvestmentDate(investmentDate: Calendar,context:Context):Boolean {
    val currentDate = Calendar.getInstance()
    if(investmentDate.timeInMillis > currentDate.timeInMillis) {
        Toast.makeText(context, "Cant input future InvestmentDate date  ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}

private fun validateMaturityDate(investmentDate: Calendar,maturityDate: Calendar,context:Context):Boolean {
    if(investmentDate.timeInMillis > maturityDate.timeInMillis) {
        Toast.makeText(context, "Cant input earlier Maturity date than InvestmentDate  ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}

private fun validateInterestRate(interestRate: String,context:Context):Boolean {
    if(interestRate.isEmpty()||interestRate.isBlank()) {
        Toast.makeText(context, "Cant have a blank interestRate field ", Toast.LENGTH_LONG)
            .show()
        return false
    }

    try {
        interestRate.toFloat()
    } catch (ex: NumberFormatException) {
        // Not a float
        Toast.makeText(context, "Cant have a non digit field in interest rate ", Toast.LENGTH_LONG)
            .show()
        return false
    }

    return true
}
