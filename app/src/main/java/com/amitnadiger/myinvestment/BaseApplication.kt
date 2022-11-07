package com.amitnadiger.myinvestment

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import com.amitnadiger.myinvestment.securityProvider.IDataStore
import com.amitnadiger.myinvestment.utility.DataStoreConst
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.FULL_NAME
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.IS_DARK_MODE
import com.amitnadiger.myinvestment.utility.DataStoreConst.Companion.SECURE_DATASTORE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BaseApplication() : Application(){

   // private val context: Context = applicationContext
    /*
    private val dataStoreProvider = DataStoreHolder.getDataStoreProvider(context,SECURE_DATASTORE,false)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var isDarkMode:Boolean? = false
    init {
        coroutineScope.launch {
            isDarkMode = dataStoreProvider.getBool(IS_DARK_MODE).first()
        }
    }
     */
    companion object {
       var isDark = mutableStateOf(false)
       private var dataStoreProvider: IDataStore? = null
       val coroutineScope = CoroutineScope(Dispatchers.IO)
       fun toggleLightTheme(context: Context){
           dataStoreProvider = DataStoreHolder.getDataStoreProvider(context,
               DataStoreConst.UNSECURE_DATASTORE,false)
           isDark.value = !isDark.value
           coroutineScope.launch {
               dataStoreProvider!!.putBool(IS_DARK_MODE,isDark.value)
           }
       }

    }
}