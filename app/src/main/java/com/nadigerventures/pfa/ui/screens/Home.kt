package com.nadigerventures.pfa.ui.screens


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat

import androidx.navigation.NavHostController
import com.nadigerventures.pfa.MainActivity
import com.nadigerventures.pfa.room.Product
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.ui.screens.fragment.ProductListFragment
import com.nadigerventures.pfa.ui.screens.fragment.nod

import com.nadigerventures.pfa.utility.*

import com.nadigerventures.pfa.viewModel.FinProductViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

private val TAG = "Home"

private var isNotificationTimerFired = mutableStateOf(false)
private var isNotificationAgreedByUser = mutableStateOf(false)
var isSortingOrderChanged = mutableStateOf(false)

private val coroutineScope = CoroutineScope(Dispatchers.Main)

@Composable
fun Home(navController: NavHostController,viewModel: FinProductViewModel,padding:PaddingValues) {
    val allProducts by viewModel.allAccounts.observeAsState(listOf())
    val context = LocalContext.current

    //Log.i(TAG,"Current thread outside launch : ${Thread.currentThread()}")

    val dataStoreProvider = DataStoreHolder.getDataStoreProvider(
        context,
        DataStoreConst.SECURE_DATASTORE, true
    )
    runBlocking {
        nod.value = dataStoreProvider.getString(DataStoreConst.NOTIFICATION_DAYS).first() ?: "30"
    }
    var advanceNotifyDays by remember { mutableStateOf(nod.value.toInt()) }

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
    var totalInvestmentAmount = 0.0
    var totalMaturityAmount = 0.0

    if(allProducts.isNotEmpty()) {
        for(i in allProducts) {
            totalInvestmentAmount +=i.investmentAmount
            totalMaturityAmount +=i.maturityAmount
        }
    } else {
        HomeDisplaySettingFragment(displayItemListOfBlankHome)
    }

    if(isSortingOrderChanged.value) {
        ProductListFragment(
            navController,
            allProducts = allProducts,
            null,
            totalInvestmentAmount,
            totalMaturityAmount,
            padding,
            "Home",
            null
        )

/*
        ProductListScreen(
            navController,
            allProducts = allProducts,
            padding,
            "Home",
            displayItemListOfBlankHome,
            advanceNotifyDays
        )

 */
        isSortingOrderChanged.value = false
    }

    ProductListFragment(
        navController,
        allProducts = allProducts,
        null,
                totalInvestmentAmount,
        totalMaturityAmount,
        padding,
        "Home",
        null
    )
/*
    ProductListScreen(
          navController,
        allProducts = allProducts,
        padding,
         "Home",
         displayItemListOfBlankHome,
         advanceNotifyDays
    )

 */

    if(isNotificationTimerFired.value) {
        //Log.i(TAG," Home => isNotificationTimerFired.value is true ")
        notifyToUser(context,allProducts,advanceNotifyDays)
    }
}

fun triggerNotification() {
    isNotificationTimerFired.value = true
    //Log.i(TAG," triggerNotification called ");
}

private fun notifyToUser(context: Context,allProducts: List<Product>,advanceNotifyDays:Int) {
   // Log.i(TAG," inside notifyToUser");
   // Log.i(TAG,"Current thread outside launch : ${Thread.currentThread()}")
   // Log.i(TAG,"Current coroutine outside: $CoroutineName")
    coroutineScope.launch(Dispatchers.Default) {

       // Log.e(TAG,"Current thread inside launch : ${Thread.currentThread()}")
       // Log.e(TAG,"Current coroutine: $CoroutineName")
        val dataStoreProvider = DataStoreHolder.getDataStoreProvider(
            context,
            DataStoreConst.SECURE_DATASTORE, true
        )
        runBlocking {
            isNotificationAgreedByUser.value =
                dataStoreProvider.getBool(DataStoreConst.IS_NOTIFICATION_ENABLED).first()?:false
        }
        if(allProducts.isNotEmpty() &&
            isNotificationAgreedByUser.value) {

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

            val notificationString = prepareNotificationMessage(numberOfAlreadyMaturedItems,
                numberOfToBeMaturedItemsWithinAdvanceNotification,
                advanceNotifyDays)
            notifyMaturedAndToBeMaturedItems(context = context,notificationString,
                numberOfAlreadyMaturedItems+numberOfToBeMaturedItemsWithinAdvanceNotification
                )
        }
    }

}
private fun createNotification(notificationManager: NotificationManager, context: Context,
                               notificationString:String?,
                               totalNumberOfItemsToBeNotified:Int) {

    //Log.i(TAG,"Notification message = textDesc = $notificationString")
    val CHANNEL_ID = "pfa_01"
    if(notificationString == null) return
    val name: CharSequence = "Investments Alert!" // The user-visible name of the channel.

    val importance = NotificationManager.IMPORTANCE_MAX
    val notifyID = 1

    val intent = Intent(context, MainActivity::class.java).apply {
        //flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


    var notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("Attention on Investments!")
        .setContentText(notificationString)
        .setSmallIcon(com.nadigerventures.pfa.R.drawable.ic_logo_notify)
        .setChannelId(CHANNEL_ID)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(notificationString)
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
        .setPriority(importance)
        .setNumber(totalNumberOfItemsToBeNotified)
        .build()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        Log.i(TAG,"Notification message is = $notificationString")
        val mChannel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH)
        mChannel.canShowBadge()
        mChannel.setShowBadge(true)
        notificationManager.createNotificationChannel(mChannel)
    }
    notificationManager.notify(notifyID, notification)
    isNotificationTimerFired.value = false
}


private fun notifyMaturedAndToBeMaturedItems(context: Context, notificationString:String?,
totalNumberOfItemsToBeNotified:Int) {
   // Log.i(TAG,"in notifyMaturedAndToBeMaturedItems")

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    createNotification(notificationManager,context,notificationString,totalNumberOfItemsToBeNotified)
}

private fun prepareNotificationMessage(maturedItems:Int,
                                       aboutToBeMaturedItems:Int,
                                       advanceNotifyDays:Int
):String? {
    if(maturedItems <= 0 &&
        aboutToBeMaturedItems <=0) {
        //Log.i(TAG,"in createNotificationChannel return since Zero items ")
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
    return ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        enableFab = true,
        screenOnBackPress = "ExitApp",
        enableAction = true,
        enableFilter = true,
        enableSort= true,
        topAppBarTitle = "My Investments",
        bottomAppBarTitle = "",
        fabString = "add",
        fabColor = Color.Red
    )
}


