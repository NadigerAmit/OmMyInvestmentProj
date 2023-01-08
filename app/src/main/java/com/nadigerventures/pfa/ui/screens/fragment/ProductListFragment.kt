package com.nadigerventures.pfa.ui.screens.fragment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.room.Product
import com.nadigerventures.pfa.ui.screens.*
import com.nadigerventures.pfa.utility.*
import java.text.NumberFormat
import java.util.*

private val TAG = "ProductListFragment"
var nod = mutableStateOf("30")

@Composable
fun ProductListFragment(
    navController: NavHostController,
    allProducts: List<Product>,
    searchResults:List<Product>?,
    totalInvestmentAmount:Double,
    totalMaturityAmount:Double,
    padding: PaddingValues,
    triggeringScreen:String,
    searchFieldList:List<String>? = null,
    advanceNotifyDays:Int = 30,

 ) {

    var searchByField by rememberSaveable { mutableStateOf("") }
    var operationField by rememberSaveable { mutableStateOf("") }
    var valueField by rememberSaveable { mutableStateOf("") }
    Log.e(TAG,"Inside ProductListFragment ")

    var finalList:MutableList<Product>?= null
    finalList = when(triggeringScreen) {
        "SearchProduct" -> {
            searchResults?.toMutableList()
        } else -> {
            allProducts.toMutableList()
        }
    }

    finalList?.add(
        Product("00000000000",
            "","","TotalInvestmentAmount",0.0, Calendar.getInstance(), Calendar.getInstance(),1.1,
            0.0f,0)
    )
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(5.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            if(triggeringScreen == "SearchProduct") {
                searchByField = DropDownBox(searchFieldList!!,"Search by",280.dp, searchByFieldValue.value)
                operationField = DropDownBox(
                    getOperationList(searchByField),"Operation",150.dp,
                    operationFieldValue.value)
                valueField = DropDownBox(
                    getValuesList(searchByField,allProducts),"Value",280.dp,
                    valueFieldValue.value)
                if(searchByFieldValue.value != searchByField) {
                    searchByFieldValue.value = searchByField
                    isSearchQueryUpdated.value = true
                }
                if(operationFieldValue.value != operationField) {
                    operationFieldValue.value = operationField
                    isSearchQueryUpdated.value = true
                }
                if(valueFieldValue.value != valueField) {
                    valueFieldValue.value = valueField
                    isSearchQueryUpdated.value = true
                }
            }


            if(finalList != null &&
                    finalList.size>1) {
                TitleRow(head1 = " Fin Ins\n\n AccNum\n\n Product ",
                    head2 = " Dep.amount\n\n Mat.amount\n\n Int.Rte %",
                    head3 =" Dep.Date\n\n Mat.Date\n\n Investor")

                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.Black)
                ) {

                    items(finalList) { product ->
                        var color: Color = getProductColor(product, nod.value.toInt())

                        if(product.accountNumber == "00000000000"
                            /*&& totalInvestmentAmount != 0.0*/
                             ) {

                            Log.e(TAG,"Inside the summary row ")
                            SummaryRow(
                                "\n  Σ Investments\n\n"+
                                        "  Σ InvestAmount\n\n"+
                                        "  Σ MaturityAmount\n\n","\n  "+"${finalList.size-1}\n\n"
                                        +"  "+ NumberFormat.getInstance().format(totalInvestmentAmount)
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
                                navController, color,triggeringScreen
                            )
                        }
                    }
                }
            }
        }
    }
}
