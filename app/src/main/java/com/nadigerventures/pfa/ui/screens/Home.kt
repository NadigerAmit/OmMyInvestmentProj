package com.nadigerventures.pfa.ui.screens


import android.annotation.SuppressLint
import android.util.Log

import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavHostController
import com.nadigerventures.pfa.securityProvider.DataStoreHolder

import com.nadigerventures.pfa.utility.*

import com.nadigerventures.pfa.viewModel.FinProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val TAG = "Home"

var isNotificationTimerFired = mutableStateOf(false)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Home(navController: NavHostController,viewModel: FinProductViewModel,padding:PaddingValues) {
    val allProducts by viewModel.allAccounts.observeAsState(listOf())
    //val searchResults by viewModel.searchResults.observeAsState(listOf())
    val displayItemListOfBlankHome = listOf(
        //  "DisplayImage",
        "statementTextForHome",
        "statementText1",
        "maturedItemsText",
        "aboutToMaturedItemsText",
        "normalItemsText",
        "GuideToAddItems",
      //  "ImageToAddItems",
    )
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    var advanceNotifyDays by remember { mutableStateOf(nod.value.toInt()) }
    val context = LocalContext.current
    val dataStoreProvider = DataStoreHolder.getDataStoreProvider(
        context,
        DataStoreConst.SECURE_DATASTORE, true
    )
    runBlocking {
        nod.value = dataStoreProvider.getString(DataStoreConst.NOTIFICATION_DAYS).first() ?: "30"
    }

    ProductListScreen(
        navController,
        allProducts = allProducts,
        // searchResults = searchResults,
        padding,
        "ProductDetail",
        displayItemListOfBlankHome,
                advanceNotifyDays
    )


    var numberOfAlreadyMaturedItems:Int = 0
    var numberOfToBeMaturedItemsWithinAdvanceNotification:Int = 0
    var numberOfToBeMaturedItemsAfterAdvanceNotification:Int = 0

    for(i in allProducts) {
        when(getProductMaturityStatus(i,advanceNotifyDays)) {
            MaturedItems.ALREADY_MATURED -> numberOfAlreadyMaturedItems++
            MaturedItems.TO_BE_MATURED_WITHIN_ADVANCED_SETTING -> numberOfToBeMaturedItemsWithinAdvanceNotification++
            MaturedItems.TO_BE_MATURED_AFTER_ADVANCED_SETTING -> numberOfToBeMaturedItemsAfterAdvanceNotification++
        }
    }

        if(isNotificationTimerFired.value) {
            coroutineScope.launch {
                val notificationString = prepareNotificationMessage(numberOfAlreadyMaturedItems,
                    numberOfToBeMaturedItemsWithinAdvanceNotification,
                    advanceNotifyDays)
                notifyMaturedAndToBeMaturedItems(context = context,notificationString)
            }
     /*
            if(notificationString == null) {
                Log.e(TAG,"notificationString = $notificationString")
                // isNotificationAllowed.value = false
            }

      */
    }
}

fun prepareNotificationMessage(maturedItems:Int,
                               aboutToBeMaturedItems:Int,
                               advanceNotifyDays:Int
):String? {
    if(maturedItems <= 0 &&
        aboutToBeMaturedItems <=0) {
        Log.e(TAG,"in createNotificationChannel return since Zero items ")
        return null
    }
    var textDesc:String = ""
    var numberOfItems = 0
    if(maturedItems>0) {
        textDesc += "$maturedItems already matured."
        numberOfItems = maturedItems
    }

    if(aboutToBeMaturedItems>0) {
        if(numberOfItems == 0) {
            numberOfItems = aboutToBeMaturedItems
        }
        textDesc += "$aboutToBeMaturedItems to be matured within $advanceNotifyDays days"
    }
    return textDesc
}

fun getScreenConfig4Home():ScreenConfig {
    //Log.e("HomeScvreen","getScreenConfig4Home");
    /*


     */
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = true,
        screenOnBackPress = "ExitApp",
        enableAction = true,
        topAppBarTitle = "Investments", bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}


