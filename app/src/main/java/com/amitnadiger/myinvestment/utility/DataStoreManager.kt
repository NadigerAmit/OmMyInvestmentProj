package com.amitnadiger.myinvestment.utility

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.amitnadiger.myinvestment.ui.screens.SearchQuery
import com.amitnadiger.myinvestment.ui.screens.SignUpData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(val context:Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_DATASTORE")

    // Create some keys we will use them to store and retrieve the data
    companion object {
        val SEARCH_FIELD = stringPreferencesKey("SEARCH_FIELD")
        val OPERATION = stringPreferencesKey("OPERATION")
        val VALUE = stringPreferencesKey("VALUE")
        val FULL_NAME = stringPreferencesKey("FULL_NAME")
        val DOB = stringPreferencesKey("DOB")
        val IS_PASS_PROTECTION_REQ = booleanPreferencesKey("IS_PASS_PROTECTION_REQ")
        val IS_REG_COMPLETE = booleanPreferencesKey("IS_REG_COMPLETE")
        val PASSWORD = stringPreferencesKey("PASSWORD")
        val PASSWORD_HINT1 = stringPreferencesKey("PASSWORD_HINT1")
        val PASSWORD_HINT2 = stringPreferencesKey("PASSWORD_HINT2")

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

    suspend fun saveRegistrationDataToDataStore(signUpData: SignUpData) {
        context.dataStore.edit {
            it[FULL_NAME] = signUpData.fullName
            it[DOB] = signUpData.dob
            it[IS_PASS_PROTECTION_REQ] = signUpData.isPasswordProtectionReq
            it[IS_REG_COMPLETE] = signUpData.isRegistrationComplete
            it[PASSWORD] = signUpData.password
            it[PASSWORD_HINT1] = signUpData.passwordHint1
            it[PASSWORD_HINT2] = signUpData.passwordHint2
        }
    }

    fun getSearchDataFromDataStore() = context.dataStore.data.map {
        Triple(
            it[SEARCH_FIELD]?:"",
            it[OPERATION]?:"",
            it[VALUE]?:""
        )
    }

    val password : Flow<String> =
    context.dataStore.data.map { preferences ->
        preferences[PASSWORD]?:""
    }

    val isRegistrationComplete : Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[IS_REG_COMPLETE]?:false
        }

    val isPasswordProtectionRequired : Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[IS_PASS_PROTECTION_REQ]?:false
        }


    val signUpData : Flow<SignUpData> =
        context.dataStore.data.map { preferences ->
            SignUpData(
                preferences[FULL_NAME]?:"",
                preferences[DOB]?:"",
                preferences[IS_PASS_PROTECTION_REQ]?:false,
                preferences[PASSWORD]?:"",
                preferences[PASSWORD_HINT1]?:"",
                preferences[PASSWORD_HINT2]?:"",
                preferences[IS_REG_COMPLETE]?:false,
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


