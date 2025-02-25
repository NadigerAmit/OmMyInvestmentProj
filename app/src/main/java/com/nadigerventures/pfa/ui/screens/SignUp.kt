package com.nadigerventures.pfa.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.utility.CustomTextField
import com.nadigerventures.pfa.utility.DataStoreConst.Companion.PASSWORD
import com.nadigerventures.pfa.utility.DataStoreConst.Companion.SECURE_DATASTORE
import com.nadigerventures.pfa.utility.DateUtility
import com.nadigerventures.pfa.utility.handleSavingUserProfileSettingData
import com.nadigerventures.pfa.utility.validateBirtDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*


private val TAG = "SignUpScreen"

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
    var isShowProfileUpdateScreenAllowed by remember { mutableStateOf(false) }


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

   // Log.i(TAG,"onExistingPasswordConfirm => confirmPwd - $isShowProfileUpdateScreenAllowed")
   // Log.e(TAG,"isTriggerFrom => confirmPwd - $isTriggerFrom")

    val context = LocalContext.current


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

    val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,SECURE_DATASTORE,true)
    var isRegistrationComplete:Boolean? = null
    var passwd:String? = null
    runBlocking {
        isRegistrationComplete = dataStorageManager.getBool("IS_REG_COMPLETE").first()
        if(isRegistrationComplete == true) {
            passwd = dataStorageManager.getString(PASSWORD).first()
        }

    }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            items(registerItemList) { items ->
                when(items.first) {
                    "Heading" -> {
                        Text(text = "Register Details ", style = TextStyle(fontSize = 30.sp))
                    }
                    "Save" -> {
                        Button(onClick = {
                            isAlreadyRegistered = true
                            handleSavingUserProfileSettingData(isPasswordProtectRequired,
                                fullName,
                                password,
                                confirmPassword,
                                context,
                                birthDate,
                                passwordHint1,
                                passwordHint2,
                                navController
                            )
                        }) {
                            Text("Save")
                        }
                        Spacer(modifier = Modifier.width(40.dp).height(90.dp))

                    }
                    "BirthDate"-> {
                        if(isPasswordProtectRequired){
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
                        }
                    }
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
                        LabelledCheckbox("PasswordProtectionForAppStart",
                            isPasswordProtectRequired,
                            onPasswordProtectRequiredFieldChange)
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
   // }
}

fun getScreenConfig4SignUpScreen():ScreenConfig {
    //Log.i("Login","getScreenConfig4SignUpScrean");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = false,
        enableDrawer = false,
        screenOnBackPress = NavRoutes.Home.route,
        enableFab = false,
        enableFilter = false,
        enableSort= false,
        topAppBarTitle = "User profile Setting",
        bottomAppBarTitle = "",
        fabString = "",
    )
}

@Composable
fun LabelledCheckbox(textString:String,
                     isChecked:Boolean,
                     onChange: (Boolean) -> Unit = {},startPaddingForCheckBox: Dp =0.dp) {
    Row(modifier = Modifier.padding(8.dp)) {
        val isChecked = remember { mutableStateOf(isChecked?:false) }

        Text(text = textString, modifier = Modifier.padding(top = 12.dp))
        Checkbox(
         //   modifier = Modifier.padding(start = startPaddingForCheckBox),
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it
                onChange(it)},
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green)
        )
    }
}