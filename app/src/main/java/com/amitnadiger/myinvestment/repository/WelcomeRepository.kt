package com.amitnadiger.myinvestment.repository

import android.content.Context
import android.util.Log
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import com.amitnadiger.myinvestment.utility.DataStoreConst

import kotlinx.coroutines.flow.first

import kotlinx.coroutines.runBlocking

private val TAG = "WelcomeRepository"
class WelcomeRepository (context: Context) {
   private val dataStoreProvider = DataStoreHolder.getDataStoreProvider(context,
        DataStoreConst.SECURE_DATASTORE,true)


    suspend fun saveOnBoardingState(completed: Boolean) {
        Log.e(TAG,"saveOnBoardingState completed = $completed")
        dataStoreProvider.putBool(DataStoreConst.IS_ON_BOARDING_COMPLETED,completed)
    }

    fun readOnBoardingState(): Boolean {
        var isOnBoardingComplete:Boolean? = false
        runBlocking {
            isOnBoardingComplete =  dataStoreProvider.getBool(DataStoreConst.IS_ON_BOARDING_COMPLETED).first()
        }
        if(isOnBoardingComplete == null) {
            Log.e(TAG,"isOnBoardingComplete is null")
            return false
        }
        Log.e(TAG,"isOnBoardingComplete  $isOnBoardingComplete")
        return isOnBoardingComplete!!
    }
}