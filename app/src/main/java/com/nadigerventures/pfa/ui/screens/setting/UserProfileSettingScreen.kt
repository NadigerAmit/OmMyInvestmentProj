package com.nadigerventures.pfa.ui.screens.setting

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.ui.screens.LabelledCheckbox
import com.nadigerventures.pfa.ui.screens.ScreenConfig
import com.nadigerventures.pfa.utility.*
import com.nadigerventures.pfa.utility.DateUtility.Companion.getCalendar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*

private val TAG = "UserProfileSetting"
@Composable
fun UserProfileSetting(navController: NavHostController,
                       padding: PaddingValues) {

    val dateFormat = "yyyy-MM-dd"
    Log.e(TAG,"SignUpScreen entered")
    val context = LocalContext.current

    val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,
        DataStoreConst.SECURE_DATASTORE,true)
    var isRegistrationComplete:Boolean? = null
    var fname:String? = null
    var dob:String? = null
    var isPasswdReq:Boolean? = false
    var passwd:String? = null
    var ph1:String? = null
    var ph2:String? = null
    runBlocking {
        isRegistrationComplete = dataStorageManager.getBool("IS_REG_COMPLETE").first()
        if(isRegistrationComplete == true) {
            fname = dataStorageManager.getString(DataStoreConst.FULL_NAME).first()
            dob = dataStorageManager.getString(DataStoreConst.DOB).first()
            isPasswdReq = dataStorageManager.getBool(DataStoreConst.IS_PASS_PROTECTION_REQ).first()
            if(isPasswdReq == true) {
                passwd = dataStorageManager.getString(DataStoreConst.PASSWORD).first()
                ph1 = dataStorageManager.getString(DataStoreConst.PASSWORD_HINT1).first()
                ph2 = dataStorageManager.getString(DataStoreConst.PASSWORD_HINT2).first()
            }
        }
    }

    var fullName by remember { mutableStateOf(fname?:"") }
    var birthDate by remember { mutableStateOf( getCalendar(dob!!,dateFormat)?:Calendar.getInstance()) }
    var isPasswordProtectRequired by remember { mutableStateOf(isPasswdReq?:false) }
    var password by remember { mutableStateOf(passwd?:"") }
    var confirmPassword by remember { mutableStateOf(passwd?:"") }
    var passwordHint1 by remember { mutableStateOf(ph1?:"") }
    var passwordHint2 by remember { mutableStateOf(ph2?:"") }
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


    val onExistingPasswordConfirm = { confirmPwd : Boolean ->
        isShowProfileUpdateScreenAllowed = confirmPwd
        // Log.e(TAG,"onExistingPasswordConfirm => confirmPwd - $confirmPwd")
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

    //userDetailUpdateFragment(navController,registerItemList,padding)



    if(passwd != null
        && !isShowProfileUpdateScreenAllowed ) {
        confirmPasswordScreen(fullName,passwd!!, onExistingPasswordConfirm)
    } else {
        isShowProfileUpdateScreenAllowed = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {
        Text(text = "Hello $fullName ", style = TextStyle(fontSize = 25.sp))
        if(isShowProfileUpdateScreenAllowed) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                //.background(colorResource(id = background))
                // .border(width = 1.dp, color = Color.LightGray)
            ) {
                Box(
                    modifier = Modifier.size(80.dp).clip(CircleShape).background(Color.White)
                ) {
                    Spacer(modifier = Modifier.width(30.dp))
                    Image(
                        painter = painterResource(id = com.nadigerventures.pfa.R.drawable.ic_profile),
                        contentDescription = "UserSetting",
                        colorFilter = ColorFilter.tint(Color.Black),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(30.dp))
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,
                    DataStoreConst.SECURE_DATASTORE,true)

                items(registerItemList) { items ->
                    when(items.first) {
                        "Heading" -> {
                            Text(text = "Register Details ", style = TextStyle(fontSize = 30.sp))
                        }
                        "Save" -> {
                            Button(onClick = {
                                handleSavingUserProfileSettingData(isPasswordProtectRequired,
                                    fullName,
                                    password,
                                    confirmPassword,
                                    context,
                                    birthDate,
                                    passwordHint1,
                                    passwordHint2,
                                    navController,
                                    onExistingPasswordConfirm
                                )
                        }) {
                                Text("Save")
                            }
                            Spacer(modifier = Modifier.width(40.dp).height(90.dp))
                        }
                        "BirthDate"-> {
                            if(isPasswordProtectRequired) {
                                CustomTextField(
                                    modifier = Modifier.clickable {
                                        DateUtility.showDatePickerDialog(context,items.second.first,
                                            dateFormat,items.second.second)
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
                            LabelledCheckbox("PasswordProtectionForAppStart",isPasswordProtectRequired,
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
        }
    }

}


@Composable
fun confirmPasswordScreen(fullName:String,existingPasswd:String,onPasswdConfirm: (Boolean) -> Unit = {}, ) {
    var password by remember { mutableStateOf("") }
    val onPasswordTextChange = { text : String ->
        password = text
    }
    Log.e("ConfirmPasswd","existingPasswd = $existingPasswd")
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
        //.padding(padding)
    ) {
        Text(text = "Hello $fullName ", style = TextStyle(fontSize = 25.sp))
        Text(text = "Enter password to see/edit rest of details", style = TextStyle(fontSize = 18.sp))
        Spacer(modifier = Modifier.width(40.dp).height(10.dp))

        CustomTextField(
            placeholder = "Enter current password",
            text = password,
            onChange = onPasswordTextChange ,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            visualTransformationParam = PasswordVisualTransformation()

        )
        Button(onClick = {
            if(existingPasswd.isEmpty()) {
                onPasswdConfirm(true)
            }
            if(existingPasswd == password) {
                onPasswdConfirm(true)
            }
        }) {
            Text("Confirm")
        }
    }
}

fun getScreenConfig4UserSetting(): ScreenConfig {
    //Log.e("HomeScvreen","getScreenConfig4Home");
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        screenOnBackPress = NavRoutes.Setting.route,
        enableFab = false,
        topAppBarTitle = "ProfileSettings", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}

