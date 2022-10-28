package com.amitnadiger.myinvestment.securityProvider

import android.content.Context

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