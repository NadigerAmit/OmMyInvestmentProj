package com.nadigerventures.pfa.securityProvider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*

import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class UnsecureDataStore(val context:Context, dataStoreName:String): IDataStore {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = dataStoreName)

     override suspend fun putString(key:String, value:String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun getString(key:String) = context.dataStore.data.map {
        it[stringPreferencesKey(key)]
    }

    override suspend fun putInt(key:String,value:Int) {
        context.dataStore.edit {
            it[intPreferencesKey(key)] = value
        }
    }

    override suspend fun getInt(key:String) = context.dataStore.data.map {
        it[intPreferencesKey(key)]
    }

    override suspend fun putLong(key:String,value:Long) {
        context.dataStore.edit {
            it[longPreferencesKey(key)] = value
        }
    }

    override suspend fun getLong(key:String) = context.dataStore.data.map {
        it[longPreferencesKey(key)]
    }

    override suspend fun getDouble(key:String) = context.dataStore.data.map {
        it[doublePreferencesKey(key)]
    }

    override suspend fun getFloat(key:String) = context.dataStore.data.map {
        it[floatPreferencesKey(key)]
    }

    override suspend fun getBool(key:String) = context.dataStore.data.map {
        it[booleanPreferencesKey(key)]
    }

    override suspend fun putFloat(key:String,value:Float)  {
        context.dataStore.edit {
            it[floatPreferencesKey(key)] = value
        }

    }

    override suspend fun removeKey(key: String,type:String) {
        context.dataStore.edit {
            when(type) {
                "Bool" -> {
                    it.remove(booleanPreferencesKey(key))
                }
                "Float" -> {
                    it.remove(floatPreferencesKey(key))
                }
                "Int"-> {
                    it.remove(intPreferencesKey(key))
                }
                "String"-> {
                    it.remove(stringPreferencesKey(key))
                }
                "Double"-> {
                    it.remove(doublePreferencesKey(key))
                }
                "Long"-> {
                    it.remove(longPreferencesKey(key))
                }
            }
        }
    }

    override suspend fun putBool(key:String,value:Boolean) {
        context.dataStore.edit {
            it[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun putDouble(key: String, value: Double) {
        context.dataStore.edit {
            it[doublePreferencesKey(key)] = value
        }
    }
}



