package com.nadigerventures.pfa

import android.app.Application
import android.content.Context
import android.util.Log
import com.nadigerventures.pfa.myWorkers.MyDailyWorker
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val TAG = "BaseApplication"
class BaseApplication() : Application(){

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    init {
        //Log.i(TAG,"Init-")
    }
    override fun onCreate() {
        super.onCreate()
       // Log.i(TAG,"Current thread: ${Thread.currentThread()}")
       // Log.i(TAG,"onCreate- Creating work request")

        coroutineScope.launch(Dispatchers.Default) {
            //Log.i(TAG,"Current coroutine: $CoroutineName")
            //Log.i(TAG,"Current thread: ${Thread.currentThread()}")
            MyDailyWorker.createWorkRequest(applicationContext)
        }
    }
}