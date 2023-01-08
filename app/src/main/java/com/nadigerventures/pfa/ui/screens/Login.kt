package com.nadigerventures.pfa.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.ui.isLogInDone
import com.nadigerventures.pfa.utility.CustomTextField
import com.nadigerventures.pfa.utility.DataStoreConst
import com.nadigerventures.pfa.utility.DataStoreConst.Companion.PASSWORD
import com.nadigerventures.pfa.utility.DataStoreConst.Companion.SECURE_DATASTORE
import com.nadigerventures.pfa.utility.DateUtility
import com.nadigerventures.pfa.utility.validateBirtDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*

private const val TAG = "LoginPage"
@Composable
fun LoginPage(navController: NavHostController,padding: PaddingValues) {
    val context = LocalContext.current

    var birthDate by remember { mutableStateOf( Calendar.getInstance()) }
    var isForgotPassword by remember { mutableStateOf( false) }
    var dobAsStr = DateUtility.getPickedDateAsString(
        birthDate.get(Calendar.YEAR),
        birthDate.get(Calendar.MONTH),
        birthDate.get(Calendar.DAY_OF_MONTH)
        ,dateFormat)

    val onBirthDateChange = { text : String ->
        //Log.e("onInDChange","investmentDate = $text")
        birthDate = DateUtility.getCalendar(text,dateFormat)
    }

    val loginItemList = listOf(
        // Pair("Heading",Pair("") { _: String -> }),
        "LoginInputs",
        "BirthDateInput",
        "PassWorldHint",
    )

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
           // .fillMaxWidth()
            .padding(30.dp)
    ) {
        items(loginItemList) { item ->
            when(item) {
                "LoginInputs" -> {
                    val password = remember { mutableStateOf(TextFieldValue()) }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Login", style = TextStyle(fontSize = 40.sp))

                    Spacer(modifier = Modifier.height(20.dp))

                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        label = { Text(text = "Password") },
                        value = password.value,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        onValueChange = { password.value = it })


                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                if(isLoginAllowed(password.value.text,context)) {
                                    navController.navigate(NavRoutes.Home.route)  {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            popUpTo(route) {
                                                saveState = true
                                            }
                                        }
                                        launchSingleTop = true
                                    }
                                    isLogInDone = true
                                } else {
                                    Toast.makeText(context, "Incorrect Password !!", Toast.LENGTH_LONG)
                                        .show()
                                }
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Login")
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    ClickableText(
                        text = AnnotatedString("Forgot password?"),
                        onClick = {
                            isForgotPassword = true
                        },
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = MaterialTheme.colors.secondaryVariant,
                            //fontSize = 26.sp,
                            fontFamily = FontFamily.Cursive
                        )
                    )
                }
                "BirthDateInput" ->{
                    if(isForgotPassword) {
                        CustomTextField(
                            modifier = Modifier.clickable {
                                DateUtility.showDatePickerDialog(context,dobAsStr,dateFormat,onBirthDateChange)
                            },
                            text = dobAsStr,
                            placeholder = "InputBirthDate",
                            onChange = {
                                if(validateBirtDate(birthDate,
                                        "BirthDate Cant be future date",context)){
                                    onBirthDateChange
                                } },
                            isEnabled = false,
                            imeAction = ImeAction.Next
                        )
                    }
                }
                "PassWorldHint" -> {
                    if(isForgotPassword &&
                        isBirthDateVerifiedOk(birthDate,context)) {
                        val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,SECURE_DATASTORE,true)
                        var  hint1:String ? = null
                        var  hint2:String ? = null
                        runBlocking {
                            hint1 = dataStorageManager.getString(DataStoreConst.PASSWORD_HINT1).first()
                            hint2 = dataStorageManager.getString(DataStoreConst.PASSWORD_HINT2).first()
                        }

                        AlertDialog(
                            title = { Text(text = "Password Hints ",color = MaterialTheme.colors.secondaryVariant) },
                            text = { Text(text = "Password Hint1 = $hint1" +
                                    "\nPassword Hint2 = $hint2")},
                            onDismissRequest = {},
                            confirmButton = {
                                TextButton(onClick = {
                                    isForgotPassword = false
                                    birthDate = Calendar.getInstance()
                                })
                                { Text(text = "I Understand",color = MaterialTheme.colors.primary,
                                    textAlign = TextAlign.Left) }
                            },
                            shape  = MaterialTheme.shapes.medium,
                            properties= DialogProperties(dismissOnBackPress = true,
                                dismissOnClickOutside=true)
                        )
                    }
                }
            }
        }
    }
}

private fun isBirthDateVerifiedOk(dob:Calendar,context: Context):Boolean {
    val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,SECURE_DATASTORE,true)
    var  dobFromDs:String ? = null
    runBlocking {
        dobFromDs = dataStorageManager.getString(DataStoreConst.DOB).first()
    }
    val dobFromUser = DateUtility.getPickedDateAsString(
        dob.get(Calendar.YEAR),
        dob.get(Calendar.MONTH),
        dob.get(Calendar.DAY_OF_MONTH), dateFormat)
    if(dobFromDs == dobFromUser) {
        return true
    }
    return false
}


fun isLoginAllowed(passwd:String,context: Context):Boolean {
    val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,
        SECURE_DATASTORE,true)
    var isLoginOk = false
    runBlocking {
        if(dataStorageManager.getString(PASSWORD).first() == passwd)
            isLoginOk = true
    }
    return isLoginOk
}

fun getScreenConfig4Login():ScreenConfig {
   // Log.e("Login","getScreenConfig4Login");
    return ScreenConfig(
        enableTopAppBar = false,
        enableBottomAppBar = false,
        enableDrawer = false,
        screenOnBackPress = NavRoutes.Home.route,
        enableFab = false,
        enableAction = false,
        enableFilter = false,
        enableSort= false,
        topAppBarTitle = "", bottomAppBarTitle = "",
        fabString = "",
    )
}