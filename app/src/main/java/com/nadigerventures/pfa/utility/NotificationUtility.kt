package com.nadigerventures.pfa.utility

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.nadigerventures.pfa.MainActivity
import com.nadigerventures.pfa.ui.screens.isNotificationTimerFired


private val TAG = "Notification"


fun createNotificationChannel(notificationManager: NotificationManager, context: Context,
                              notificationString:String?) {

    Log.e(TAG,"Notification message = textDesc = $notificationString")
    val CHANNEL_ID = "pfa_01"
    if(notificationString == null) return
    val name: CharSequence = "Investments Alert!" // The user-visible name of the channel.

    val importance = NotificationManager.IMPORTANCE_HIGH
    val notifyID = 1

    val intent = Intent(context, MainActivity::class.java).apply {
        //flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


    var notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("Attention on Investments!")
        .setContentText(notificationString)
        .setSmallIcon(com.nadigerventures.pfa.R.drawable.ic_logo_notif)
        .setChannelId(CHANNEL_ID)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(notificationString)
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
        .setPriority(importance)
        .setNumber(10)
        .build()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        Log.e(TAG,"Notification message is >-O= $notificationString")
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        mChannel.canShowBadge()
        mChannel.setShowBadge(true)
        notificationManager.createNotificationChannel(mChannel)
    }
    notificationManager.notify(notifyID, notification)
    isNotificationTimerFired.value = false
}


fun notifyMaturedAndToBeMaturedItems(context: Context, notificationString:String?) {
    Log.e(TAG,"in notifyMaturedAndToBeMaturedItems")

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    createNotificationChannel(notificationManager,context,notificationString)
}

