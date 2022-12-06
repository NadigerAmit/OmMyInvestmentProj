package com.nadigerventures.pfa.securityProvider

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataStoreHolder {
    private val datastoreProviderMap = HashMap<String, IDataStore>()

    fun getDataStoreProvider(context: Context,prefName:String,isSecure:Boolean= false): IDataStore {
        var retDataStoreProvider:IDataStore
        if(datastoreProviderMap.containsKey(prefName)) {
            return datastoreProviderMap[prefName]!!
        }
        when (isSecure) {
            true -> {
                datastoreProviderMap[prefName] = SecureDataStore(context,prefName)
                retDataStoreProvider = datastoreProviderMap[prefName]!!
            }
            false -> {
                datastoreProviderMap[prefName] = UnsecureDataStore(context,prefName)
                retDataStoreProvider = datastoreProviderMap[prefName]!!
            }
        }
        return retDataStoreProvider
    }
}
/*
object DataStoreHolder {
    private val datastoreProviderMap = HashMap<String, IDataStore>()
    suspend
    fun getDataStoreProvider(context: Context,prefName:String,isSecure:Boolean= false): IDataStore {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        var ds = coroutineScope.async (Dispatchers.IO) {
            var retDataStoreProvider:IDataStore
            if(datastoreProviderMap.containsKey(prefName)) {
                return@async datastoreProviderMap[prefName]!!
            }
            when (isSecure) {
                true -> {
                    datastoreProviderMap[prefName] = SecureDataStore(context,prefName)
                    retDataStoreProvider = datastoreProviderMap[prefName]!!
                }
                false -> {
                    datastoreProviderMap[prefName] = UnsecureDataStore(context,prefName)
                    retDataStoreProvider = datastoreProviderMap[prefName]!!
                }
            }
            return@async retDataStoreProvider
        }.await()
        return ds
    }
}
 */