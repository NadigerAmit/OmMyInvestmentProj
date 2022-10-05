package com.amitnadiger.myinvestment.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.utility.DateUtility
import com.amitnadiger.myinvestment.utility.DateUtility.Companion.getNumberOfDaysBetweenTwoDays
import com.amitnadiger.myinvestment.viewModel.FinProductViewModel
import java.util.*


@Composable
fun AddProduct(navController: NavHostController,viewModel: FinProductViewModel,padding: PaddingValues) {
    Log.e("AddProductScreen","Adding the product ");
    /*
    BackHandler(enabled = true) {
        navController.navigate(NavRoutes.Home.route )
    }
     */
    screenSetUpInAddProductScreen(viewModel,padding)
}

fun getScreenConfig4AddProduct():ScreenConfig {
    return ScreenConfig(true,
        true,
        true,
        false,
        "Add Investment","",
        "",
    )
}


@Composable
fun screenSetUpInAddProductScreen(viewModel: FinProductViewModel,padding:PaddingValues)  {

    val dateFormat = "yyyy-MM-dd"

    var accountNumber by remember { mutableStateOf("") }
    var finInstitutionName by remember { mutableStateOf("") }
    var investorName by remember { mutableStateOf("") }
    var investmentAmount by remember { mutableStateOf("") }
    var investmentDate by remember { mutableStateOf(Calendar.getInstance()) }
    var maturityDate by remember { mutableStateOf(Calendar.getInstance()) }
    var maturityAmount by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var productType by remember { mutableStateOf("") }
    var nomineeName by remember { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }

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

    val productDetailItemList = listOf(
        Pair("AccNum",Pair(accountNumber,onAccountNumTextChange)),
        Pair("InvestmentAmount",Pair(investmentAmount,onInvestmentAmountTextChange)),
        Pair("InvestorName",Pair(investorName,onInvestorNameTextChange)),
        Pair("FinInstitutionName",Pair(finInstitutionName,onFinInstitutionNameTextChange)),
        Pair("ProductType",Pair(productType,onProductTypeTextChange)),
        Pair("InvestmentDate",Pair(DateUtility.getPickedDateAsString(
            investmentDate.get(Calendar.YEAR),
            investmentDate.get(Calendar.MONTH),
            investmentDate.get(Calendar.DAY_OF_MONTH)
            ,dateFormat),onInvestmentDateTextChange)),
        Pair("MaturityDate",Pair(DateUtility.getPickedDateAsString(
            maturityDate.get(Calendar.YEAR),
            maturityDate.get(Calendar.MONTH),
            maturityDate.get(Calendar.DAY_OF_MONTH)
            ,dateFormat),onMaturityDateTextChange)),
        Pair("MaturityAmount",Pair(maturityAmount,onMaturityAmountTextChange)),
        Pair("InterestRate",Pair(interestRate,onInterestRateTextChange)),
        Pair("NomineeName",Pair(nomineeName,onNomineeNameTextChange)),
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
                "Save" ->{
                    Button(onClick = {
                        fun validateInputs():Boolean {
                            Log.e("AddProduct","In validateInputs ")
                            if(!validateAccountNumber(accountNumber,context) ||
                                !validateFinInstitution(finInstitutionName,context) ||
                                !validateInvestmentDate(investmentDate,context) ||
                                !validateMaturityDate(investmentDate,maturityDate,context) ||
                                !validateInvestorName(investorName,context) ||
                                !validateInvestmentAmount(investmentAmount,context) ||
                                !validateMaturityAmount(maturityAmount,context) ||
                                !validateInterestRate(interestRate,context)
                            )
                                return false
                            return true
                        }
                        if(validateInputs()) {
                            viewModel.insertFinProduct(
                                Product(
                                    accountNumber,
                                    finInstitutionName,
                                    productType,
                                    investorName,
                                    investmentAmount.toInt(),
                                    investmentDate,
                                    maturityDate,
                                    maturityAmount.toDouble(),
                                    interestRate.toFloat(),
                                    getNumberOfDaysBetweenTwoDays(maturityDate,investmentDate).toInt(),
                                    nomineeName
                                )
                            )

                            Toast.makeText(context, "Saving to DB", Toast.LENGTH_LONG)
                                .show()
                        }
                    }) {
                        Text("Save")
                    }
                }
                "InvestmentDate",
                "MaturityDate"->
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
                "InvestmentAmount",
                "MaturityAmount",
                "InterestRate" -> {
                    CustomTextField(
                        placeholder = items.first,
                        text = items.second.first.toString(),
                        onChange = items.second.second,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                }
                else -> {
                    CustomTextField(
                        placeholder = items.first,
                        text = items.second.first.toString(),
                        onChange = items.second.second,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                }
            }
        }
    }
}




@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        label = { Text(placeholder) },
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyBoardActions,
        enabled = isEnabled,

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black
        ),
        /*
             placeholder = {
                 Text(text = placeholder, style = TextStyle(fontSize = 18.sp, color = Color.LightGray))
             }
         */
    )
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
    return true
}

private fun validateMaturityAmount(maturityAmount: String,context:Context):Boolean {
    if(maturityAmount.isEmpty()||maturityAmount.isBlank()) {
        Toast.makeText(context, "Cant have a blank maturityAmount field ", Toast.LENGTH_LONG)
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
    val currentDate = Calendar.getInstance()
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
    return true
}


//// Below functions are not used anymore

@Composable
fun CustomTextField1(
    title: String,
    textState: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        label = { Text(title) },
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(fontWeight = FontWeight.Bold,
            fontSize = 20.sp)
    )
}
/*
@Composable
private fun CustomTextFieldWithBasicText(
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize
) {
    var text by rememberSaveable { mutableStateOf("") }
    BasicTextField(modifier = modifier
        .background(
            MaterialTheme.colors.surface,
            MaterialTheme.shapes.small,
        )
        .fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (text.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}

 */