package com.amitnadiger.myinvestment.utility

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*


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