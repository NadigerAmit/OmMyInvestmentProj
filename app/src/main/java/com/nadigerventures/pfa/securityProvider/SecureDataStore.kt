package com.nadigerventures.pfa.securityProvider

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SecureDataStore(val context:Context, dataStoreName:String): IDataStore {
    private val TAG = "SecDtaStrProvider"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = dataStoreName)
    private lateinit var cryptProvider: ICryptoProvider
    private val stringMap = HashMap<String, String?>()
    private val boolMap = HashMap<String, Boolean?>()
    private val intMap = HashMap<String, Int?>()
    private val longMap = HashMap<String, Long?>()
    private val doubleMap = HashMap<String, Double?>()
    private val floatMap = HashMap<String, Float?>()
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    init {
        coroutineScope.launch {
            cryptProvider =  CryptoProvider(context)
        }
    }

    override suspend fun getBool(key: String) = context.dataStore.data.map {
       // Log.i(TAG, "getBool --> Key = $key")
        var ret: Boolean? = null
        if (boolMap.containsKey(key)) {
            ret = boolMap[key]
           // Log.i(TAG, "Found Key in boolMap value = $ret")
        } else {
            ret =  getStringValue(key).first()?.toBoolean()
            if (ret != null) {
               // Log.i(TAG, "Decrypted str after decryption = $ret")
            }
            boolMap[key] = ret
        }
        ret
    }

    override suspend fun getString(key: String) = context.dataStore.data.map {
        Log.i(TAG, "getString -->  Key = $key")
        var decryptedStr: String? = null
        if (stringMap.containsKey(key)) {
            decryptedStr = stringMap[key]
           // Log.i(TAG, "Found Key in stringMap value = $decryptedStr")
        } else {
            decryptedStr =  getStringValue(key).first()
            if (decryptedStr != null) {
             //   Log.i(TAG, "Decrypted str after decryption = $decryptedStr")
            }
            stringMap[key] = decryptedStr
        }
        decryptedStr
    }


    override suspend fun getInt(key: String) = context.dataStore.data.map {
        var intRet: Int? = null
        if (intMap.containsKey(key)) {
            intRet = intMap[key]
           // Log.i(TAG, "Found Key in intMap value = $intRet")
        } else {
            intRet =  getStringValue(key).first()?.toInt()
            if (intRet != null) {
          //      Log.i(TAG, "Decrypted str after decryption = $intRet")
            }
            intMap[key] = intRet
        }
        intRet
    }

    override suspend fun getLong(key: String) = context.dataStore.data.map {
        var longRet: Long? = null
        if (longMap.containsKey(key)) {
            longRet = longMap[key]
        //    Log.i(TAG, "Found Key in longMap value = $longRet")
        } else {
            longRet =  getStringValue(key).first()?.toLong()
            if (longRet != null) {
         //       Log.i(TAG, "Decrypted str after decryption = $longRet")
            }
            longMap[key] = longRet
        }
        longRet
    }

    override suspend fun getDouble(key: String) = context.dataStore.data.map {
        var doubleRet: Double? = null
        if (doubleMap.containsKey(key)) {
            doubleRet = doubleMap[key]
         //   Log.i(TAG, "Found Key in doubleMap value = $doubleRet")
        } else {
            doubleRet = getStringValue(key).first()?.toDouble()
            if (doubleRet != null) {
          //      Log.i(TAG, "Decrypted str after decryption = $doubleRet")
            }
            doubleMap[key] = doubleRet
        }
        doubleRet
    }

    override suspend fun getFloat(key: String) = context.dataStore.data.map {
        var floatRet: Float? = null
        if (floatMap.containsKey(key)) {
            floatRet = floatMap[key]
         //   Log.i(TAG, "Found Key in floatMap value = $floatRet")
        } else {
            floatRet = getStringValue(key).first()?.toFloat()
            if (floatRet != null) {
            //    Log.i(TAG, "Decrypted str after decryption = $floatRet")
            }
            floatMap[key] = floatRet
        }
        floatRet
    }

    override suspend fun putString(key: String, value: String) {
        stringMap[key] = value
        storeInDataStore(key, value)
    }

    override suspend fun putBool(key: String, value: Boolean) {
        boolMap[key] = value
        storeInDataStore(key, value.toString())
    }

    override suspend fun putLong(key: String, value: Long) {
        longMap[key] = value
        storeInDataStore(key, value.toString())
    }

    override suspend fun putInt(key: String, value: Int) {
        intMap[key] = value
        storeInDataStore(key, value.toString())
    }

    override suspend fun putFloat(key: String, value: Float) {
        floatMap[key] = value
        storeInDataStore(key, value.toString())
    }

    override suspend fun putDouble(key: String, value: Double) {
        doubleMap[key] = value
        storeInDataStore(key, value.toString())
    }

    private suspend fun storeInDataStore(key: String, value: String) {
        val encryptedData: EncryptedData? =
            cryptProvider.encrypt(value) // This is for Value
        if (encryptedData == null) {
     //       Log.d(TAG, "Encrypted data is null ")
            return
        }
     //   Log.i(TAG, "encryptedValue.encryptedStr = ${encryptedData.encryptedStr}")
     //   Log.i(TAG, " encryptedValue.iVStr = ${encryptedData.iVStr}")

        context.dataStore.edit {
            it[stringPreferencesKey(key)] = encryptedData.encryptedStr
            it[stringPreferencesKey("$key-IV")] = encryptedData.iVStr
        }
    }

    private fun getStringValue(key: String) = context.dataStore.data.map {
        var decryptedStr:String ?= null
        val encryptedStr = it[stringPreferencesKey(key)]
        val iv = it[stringPreferencesKey("$key-IV")]
     //   Log.i(TAG, "From Data store encryptedStr = $encryptedStr")
    //    Log.i(TAG, " From Data store iVStr = $iv")
        if(encryptedStr !== null && iv != null) {
            val encryptedData = EncryptedData(
                encryptedStr, iv
            )
            decryptedStr = cryptProvider.decrypt(encryptedData)
        }
        decryptedStr
    }

    override suspend fun removeKey(key: String,type:String) {
   //     Log.d(TAG,"Key($key) is removed")
        context.dataStore.edit {
            when(type) {
                "Bool" -> {
                    it.remove(booleanPreferencesKey(key))
                    boolMap.remove(key)
                }
                "Float" -> {
                    it.remove(floatPreferencesKey(key))
                    floatMap.remove(key)
                }
                "Int"-> {
                    it.remove(intPreferencesKey(key))
                    intMap.remove(key)
                }
                "String"-> {
                    it.remove(stringPreferencesKey(key))
                    stringMap.remove(key)
                }
                "Double"-> {
                    it.remove(doublePreferencesKey(key))
                    doubleMap.remove(key)
                }
                "Long"-> {
                    it.remove(longPreferencesKey(key))
                    longMap.remove(key)
                }
            }
            it.remove(stringPreferencesKey("$key-IV"))
        }
    }
}

