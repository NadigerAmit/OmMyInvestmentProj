package com.nadigerventures.pfa.utility

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.nadigerventures.pfa.room.Product
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.ui.screens.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

private val TAG = "CommonUtility"
fun getProductColor(product:Product,advanceNotifyDays:Int): Color {
    var color: Color = Color.Unspecified
    val numberOfDays =
        DateUtility.getNumberOfDaysBetweenTwoDays(product.maturityDate, Calendar.getInstance())
    if (numberOfDays <= 0) {
        color = Color.Red
    } else if (numberOfDays <= advanceNotifyDays) {
        color = Color.Magenta
    }
    return color
}

enum class MaturedItems{
    ALREADY_MATURED,
    TO_BE_MATURED_WITHIN_ADVANCED_SETTING,
    TO_BE_MATURED_AFTER_ADVANCED_SETTING,
}

fun getProductMaturityStatus(product:Product,advanceNotifyDays:Int): MaturedItems {
    var maturityStatus: MaturedItems = MaturedItems.TO_BE_MATURED_AFTER_ADVANCED_SETTING
    val numberOfDays =
        DateUtility.getNumberOfDaysBetweenTwoDays(product.maturityDate, Calendar.getInstance())
    if (numberOfDays <= 0) {
        maturityStatus = MaturedItems.ALREADY_MATURED
    } else if (numberOfDays <= advanceNotifyDays) {
        maturityStatus = MaturedItems.TO_BE_MATURED_WITHIN_ADVANCED_SETTING
    }
    return maturityStatus
}

fun handleSavingUserProfileSettingData(isPasswordProtectRequired:Boolean,
                                       fullName:String,
                                       password:String,
                                       confirmPassword:String,
                                       context: Context,
                                       birthDate:Calendar,
                                       passwordHint1:String?,
                                       passwordHint2:String?,
                                       navController: NavHostController,
                                       onShowProfileUpdateScreenAllowedChange: (Boolean) -> Unit = {}) {
   Log.e(TAG,"handleSavingUserProfileSettingData called")
    when {
        isPasswordProtectRequired -> {
            if(!validateFullName(fullName,context)
                || !validatePassword(password,confirmPassword,context)
                || !validateBirtDate(birthDate,
                    "BirthDate Cant be future or current date",context)
            ) {

            } else {
                Log.e(TAG,"handleSavingUserProfileSettingData  Validaton passed ")
                saveSingUpInfoInDataStore(context,
                    fullName,
                    DateUtility.getPickedDateAsString(
                        birthDate.get(Calendar.YEAR),
                        birthDate.get(Calendar.MONTH),
                        birthDate.get(Calendar.DAY_OF_MONTH),
                        dateFormat
                    ),isPasswordProtectRequired,
                    password,
                    passwordHint1!!,
                    passwordHint2!!)
                navController.navigate(NavRoutes.Setting.route)  {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                }
            }
        }
        else -> {
            if(validateFullName(fullName,context)
                && validateBirtDate(birthDate,
                    "BirthDate Cant be future or current date",context)) {
                saveSingUpInfoInDataStore(context,fullName, DateUtility.getPickedDateAsString(
                    birthDate.get(Calendar.YEAR),
                    birthDate.get(Calendar.MONTH),
                    birthDate.get(Calendar.DAY_OF_MONTH),
                    dateFormat
                ),isPasswordProtectRequired,
                    password,
                    passwordHint1!!,
                    passwordHint2!!)
                onShowProfileUpdateScreenAllowedChange(false)
                navController.navigate(NavRoutes.Setting.route)  {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                }
            }
        }
    }
}

fun validateBirtDate(birthDate: Calendar, toastMessage:String,context:Context):Boolean {
    val currentDate = Calendar.getInstance()
    if(birthDate.timeInMillis >= currentDate.timeInMillis) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG)
            .show()
        Log.e("validateBirtDate","return false  ")
        return false
    }
    Log.e("validateBirtDate","return true  ")
    return true
}

fun validateFullName(fullName: String?,context:Context):Boolean {
    Log.e("validateFullName","$fullName ")
    if(fullName == null ||
        fullName.isBlank()
        ||fullName.isEmpty()
        ||fullName == "") {
        Toast.makeText(context, "FullName cant be null or Empty ", Toast.LENGTH_LONG)
            .show()
        Log.e("validateFullName","return false  ")
        return false
    }
    Log.e("validateFullName","return ture  ")
    return true
}

fun validatePassword(password: String,confirmPassword:String,context:Context):Boolean {
    Log.e("validatePassword","password = $password and confirmPassword = $confirmPassword")
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

fun saveSingUpInfoInDataStore(context:Context,fullName:String,
                              dob:String,isPasswordProtectionReq:Boolean,
                              password:String,passwordHint1:String,
                              passwordHint2:String) {

    val dataStoreProvider = DataStoreHolder.getDataStoreProvider(context,
        DataStoreConst.SECURE_DATASTORE,true)
    val coroutineScope = CoroutineScope(Dispatchers.IO)

    Log.e(TAG,"fullName = $fullName")
    coroutineScope.launch {
        dataStoreProvider.putBool(DataStoreConst.IS_PASS_PROTECTION_REQ,isPasswordProtectionReq)
        dataStoreProvider.putString(DataStoreConst.FULL_NAME,fullName)
        dataStoreProvider.putString(DataStoreConst.DOB,dob)
        dataStoreProvider.putBool(DataStoreConst.IS_REG_COMPLETE,true)
    }

    if(isPasswordProtectionReq) {
        coroutineScope.launch {
            dataStoreProvider.putString(DataStoreConst.PASSWORD,password)
            dataStoreProvider.putString(DataStoreConst.PASSWORD_HINT1,passwordHint1)
            dataStoreProvider.putString(DataStoreConst.PASSWORD_HINT2,passwordHint2)
        }
    } else {
        coroutineScope.launch {
            dataStoreProvider.removeKey(DataStoreConst.PASSWORD,"String")
            dataStoreProvider.removeKey(DataStoreConst.PASSWORD_HINT1,"String")
            dataStoreProvider.removeKey(DataStoreConst.PASSWORD_HINT2,"String")
        }

    }

}
