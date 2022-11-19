package com.amitnadiger.myinvestment.utility

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.ui.NavRoutes
import com.amitnadiger.myinvestment.ui.screens.*
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
                // Toast will be shown in the validate**
                /*
                navController.navigate(NavRoutes.Setting.route)  {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                }
                 */
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
