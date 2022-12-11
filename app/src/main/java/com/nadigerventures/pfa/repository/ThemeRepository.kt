package com.nadigerventures.pfa.repository

import android.content.Context
import android.util.Log
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.utility.DataStoreConst
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val TAG = "ThemeRepository"

class ThemeRepository(context: Context) {
    private val dataStoreProvider = DataStoreHolder.getDataStoreProvider(context,
        DataStoreConst.UNSECURE_DATASTORE,false)


    suspend fun saveTheme(completed: Boolean) {
        //Log.i(TAG,"saveOnBoardingState completed = $completed")
        dataStoreProvider.putBool(DataStoreConst.IS_DARK_MODE,completed)
    }

    fun readOnTheme(): Boolean {
        var isDarkMode:Boolean? = false
        runBlocking {
            isDarkMode =  dataStoreProvider.getBool(DataStoreConst.IS_DARK_MODE).first()
        }
        if(isDarkMode == null) {
            //Log.i(TAG,"isDarkMode is null")
            return true
        }
        //Log.i(TAG,"isOnBoardingComplete  $isDarkMode")
        return isDarkMode!!
    }
}