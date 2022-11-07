package com.amitnadiger.myinvestment.utility

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

class DataStoreConst {
    companion object {
        const val UNSECURE_DATASTORE = "DATASTORE_UNSECURE"
        const val SECURE_DATASTORE = "DATASTORE_SECURE"
        const val SEARCH_FIELD = "SEARCH_FIELD"
        const val OPERATION ="OPERATION"
        const val VALUE = "VALUE"
        const val FULL_NAME = "FULL_NAME"
        const val DOB = "DOB"
        const val IS_PASS_PROTECTION_REQ ="IS_PASS_PROTECTION_REQ"
        const val IS_REG_COMPLETE = "IS_REG_COMPLETE"
        const val PASSWORD = "PASSWORD"
        const val PASSWORD_HINT1 = "PASSWORD_HINT1"
        const val PASSWORD_HINT2 = "PASSWORD_HINT2"
        const val DB_PASSCODE = "DB_PASSCODE"
        const val IS_DARK_MODE = "IS_DARK_MODE"
    }
}