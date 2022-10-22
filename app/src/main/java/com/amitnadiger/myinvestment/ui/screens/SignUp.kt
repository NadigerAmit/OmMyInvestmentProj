package com.amitnadiger.myinvestment.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.room.ProductUpdate
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.utility.CustomTextField
import com.amitnadiger.myinvestment.utility.DataStoreManager
import com.amitnadiger.myinvestment.utility.DateUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

@Composable
fun SignUpScreen(navController: NavHostController,
                 padding: PaddingValues) {
    val dateFormat = "yyyy-MM-dd"

    var fullName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf( Calendar.getInstance()) }
    var isPasswordProtectRequired by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordHint1 by remember { mutableStateOf("") }
    var passwordHint2 by remember { mutableStateOf("") }
    var isAlreadyRegistered by remember { mutableStateOf(false) }

    val onFullNameTextChange = { text : String ->
        fullName = text
    }

    val onPasswordProtectRequiredFieldChange = { isEnable : Boolean ->
        isPasswordProtectRequired = isEnable
    }

    val onPasswordTextChange = { text : String ->
        password = text
    }

    val onConfirmPasswordTextChange = { text : String ->
        confirmPassword = text
    }

    val onBirthDateChange = { text : String ->
        //Log.e("onInDChange","investmentDate = $text")
        birthDate = DateUtility.getCalendar(text,dateFormat)
    }

    val onPasswordHint1TextChange = { text : String ->
        passwordHint1 = text
    }

    val onPasswordHint2TextChange = { text : String ->
        passwordHint2 = text
    }

    val onIsAlreadyRegisteredStateChange = { text : Boolean ->
        isAlreadyRegistered = text
    }


    val registerItemList = listOf(
       // Pair("Heading",Pair("") { _: String -> }),
        Pair("Full Name",Pair(fullName,onFullNameTextChange)),
        Pair("BirthDate",Pair(
            DateUtility.getPickedDateAsString(
                birthDate.get(Calendar.YEAR),
                birthDate.get(Calendar.MONTH),
                birthDate.get(Calendar.DAY_OF_MONTH)
                ,dateFormat),onBirthDateChange)),

        Pair("Password",Pair(password,onPasswordTextChange)),
        Pair("Confirm Password",Pair(confirmPassword,onConfirmPasswordTextChange)),
        Pair("Password Hint1",Pair(passwordHint1,onPasswordHint1TextChange)),
        Pair("Password Hint2",Pair(passwordHint2,onPasswordHint2TextChange)),
        Pair("PasswordProtectionOnAppRestart",Pair("") { _: String -> }),
        Pair("Save",Pair("") { _: String -> }),

        //Pair("Password Hint2",Pair(isAlreadyRegistered,onIsAlreadyRegisteredStateChange)),
    )

    val context = LocalContext.current

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        items(registerItemList) { items ->
            when(items.first) {
                "Heading" ->{
                    Text(text = "Register Details ", style = TextStyle(fontSize = 30.sp))
                }
                "Save" ->{
                    Button(onClick = {
                        isAlreadyRegistered = true
                        if(isPasswordProtectRequired) {
                            if(!validatePassword(password,confirmPassword,context)
                                        ||!validateBirtDate(birthDate,
                                    "BirthDate Cant be future date",context)) {
                                // Toast will be shown in the validate**
                            } else {
                                saveSingUpInfoInDataStore(fullName, DateUtility.getPickedDateAsString(
                                    birthDate.get(Calendar.YEAR),
                                    birthDate.get(Calendar.MONTH),
                                    birthDate.get(Calendar.DAY_OF_MONTH),
                                    com.amitnadiger.myinvestment.ui.screens.dateFormat
                                ),isPasswordProtectRequired,
                                    password,
                                    passwordHint1,
                                    passwordHint2,
                                    isAlreadyRegistered)
                                navController.navigate(NavRoutes.Login.route)
                            }
                        } else {
                            saveSingUpInfoInDataStore(fullName, DateUtility.getPickedDateAsString(
                                birthDate.get(Calendar.YEAR),
                                birthDate.get(Calendar.MONTH),
                                birthDate.get(Calendar.DAY_OF_MONTH),
                                com.amitnadiger.myinvestment.ui.screens.dateFormat
                            ),isPasswordProtectRequired,
                                password,
                                passwordHint1,
                                passwordHint2,
                                isAlreadyRegistered)
                            navController.navigate(NavRoutes.Home.route)
                        }
                    }) {
                        Text("Save")
                    }
                }
                "BirthDate"->
                    CustomTextField(
                        modifier = Modifier.clickable {
                            DateUtility.showDatePickerDialog(context,items.second.first,dateFormat,items.second.second)
                        },
                        text = items.second.first,
                        placeholder = items.first,
                        onChange = {
                                    if(validateBirtDate(birthDate,
                                    "BirthDate Cant be future date",context)){
                                        items.second.second
                                    } },
                        isEnabled = false,
                        imeAction = ImeAction.Next
                    )
                "Password",
                "Confirm Password" -> {
                    if(isPasswordProtectRequired) {
                        CustomTextField(
                            placeholder = items.first,
                            text = items.second.first,
                            onChange = items.second.second,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next,
                            visualTransformationParam = PasswordVisualTransformation()

                        )
                    }

                }
                "PasswordProtectionOnAppRestart" -> {
                    LabelledCheckbox("PasswordProtectionForAppStart",onPasswordProtectRequiredFieldChange)
                }
                "Password Hint1",
                "Password Hint2"-> {
                    if(isPasswordProtectRequired){
                        CustomTextField(
                            placeholder = items.first,
                            text = items.second.first,
                            onChange = items.second.second,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    }
                }
                else ->{
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

private fun validateBirtDate(investmentDate: Calendar, toastMessage:String,context:Context):Boolean {
    val currentDate = Calendar.getInstance()
    if(investmentDate.timeInMillis > currentDate.timeInMillis) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}

private fun validatePassword(password: String,confirmPassword:String,context:Context):Boolean {
    if(password != confirmPassword) {
        Toast.makeText(context, "Mismatch between Password and Confirm Password field ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    if(password.isEmpty()||password.isBlank()) {
        Toast.makeText(context, "Cant have a blank password ", Toast.LENGTH_LONG)
            .show()
        return false
    }
    return true
}

data class SignUpData(val fullName:String,
                      val dob:String,
                      val isPasswordProtectionReq:Boolean = false,
                      val password:String= "",
                      val passwordHint1:String= "",
                      val passwordHint2:String = "",
                      val isRegistrationComplete:Boolean = true)

fun saveSingUpInfoInDataStore(fullName:String,
                                dob:String,isPasswordProtectionReq:Boolean,
                                password:String,passwordHint1:String,
                              passwordHint2:String,
                                isRegistrationComplete:Boolean) {
    val dataStorageManager = DataStoreManager.getLocalDataStoreManagerInstance()
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    coroutineScope.launch {
        dataStorageManager.saveRegistrationDataToDataStore(
            SignUpData(fullName,
                        dob,
                        isPasswordProtectionReq,
                        password,passwordHint1,
                        passwordHint2,
                        isRegistrationComplete)
                    )
    }
}



fun getScreenConfig4SignUpScrean():ScreenConfig {
    Log.e("Login","getScreenConfig4SignUpScrean");
    return ScreenConfig(true,
        false,
        false,
        false,
        "Register Details","",
        "",
    )
}

@Composable
fun LabelledCheckbox(textString:String,onChange: (Boolean) -> Unit = {},) {
    Row(modifier = Modifier.padding(8.dp)) {
        val isChecked = remember { mutableStateOf(false) }

        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it
                onChange(it)},
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green)
        )
        //Text(text = "Enable Password Protection On AppRestart")
        Text(text = textString, modifier = Modifier.padding(top = 12.dp))
    }
}