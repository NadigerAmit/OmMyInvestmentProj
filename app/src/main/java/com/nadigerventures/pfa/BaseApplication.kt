package com.nadigerventures.pfa

import android.app.Application
import android.util.Log
import com.nadigerventures.pfa.notificationWorkManager.createWorkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val TAG = "BaseApplication"
class BaseApplication() : Application() {


   // private val context: Context = applicationContext

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    init {
       Log.e(TAG,"Init-")

    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG,"onCreate- Creating work request")
        coroutineScope.launch(Dispatchers.Default) {
            createWorkRequest(applicationContext)
        }
    }
}