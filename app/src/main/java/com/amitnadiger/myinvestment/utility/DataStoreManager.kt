package com.amitnadiger.myinvestment.utility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.amitnadiger.myinvestment.ui.screens.SearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(val context:Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_DATASTORE")

    // Create some keys we will use them to store and retrieve the data
    companion object {
        val SEARCH_FIELD = stringPreferencesKey("SEARCH_FIELD")
        val OPERATION = stringPreferencesKey("OPERATION")
        val VALUE = stringPreferencesKey("VALUE")
        lateinit var dataStoreManager: DataStoreManager

        fun initializeDataStoreManager(context: Context) {
            dataStoreManager = DataStoreManager(context)
        }
        fun getLocalDataStoreManagerInstance():DataStoreManager {
            return dataStoreManager
        }
    }

    suspend fun saveSearchDataToDataStore(searchQuery:SearchQuery) {
        context.dataStore.edit {

            it[SEARCH_FIELD] = searchQuery.searchByFieldValue
            it[OPERATION] = searchQuery.operationFieldValue
            it[VALUE] = searchQuery.valueFieldValue
        }
    }

    fun getSearchDataFromDataStore1() = context.dataStore.data.map {
        Triple(
            it[SEARCH_FIELD]?:"",
            it[OPERATION]?:"",
            it[VALUE]?:""
        )
    }
    val searchQueryLocal : Flow<SearchQuery> =
        context.dataStore.data.map { preferences ->
        SearchQuery(
            preferences[SEARCH_FIELD]?:"",
            preferences[OPERATION]?:"",
            preferences[VALUE]?:""
            )
        }


}


