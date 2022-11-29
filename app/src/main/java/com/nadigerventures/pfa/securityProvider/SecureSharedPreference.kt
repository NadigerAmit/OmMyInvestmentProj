package com.nadigerventures.pfa.securityProvider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log


class SecureSharedPreference {
    private val TAG = "SecureSharedPreference"
    private var mInstance: SecureSharedPreference? = null
    private var mContext: Context? = null
    private var mPref: SharedPreferences? = null
    private var cryptoProvider: CryptoProvider? = null

    constructor(context: Context) {
        mPref = PreferenceManager.getDefaultSharedPreferences(context)
        mContext = context
        cryptoProvider = CryptoProvider(context)
    }

    @Synchronized
    fun getInstance(context: Context): SecureSharedPreference? {
        if (mInstance == null) {
            mInstance = SecureSharedPreference(context)
        }
        return mInstance
    }

    fun putInt(key: String, value: Int) {
        val encryptedValue: EncryptedData? =
            cryptoProvider?.encrypt(value.toString()) // This is for Value
        if (encryptedValue == null) {
            Log.e(TAG, "Encrypted data is null ")
            return
        }
        mPref!!.edit().putString(key, encryptedValue.encryptedStr).apply()
        mPref!!.edit().putString("$key-Iv", encryptedValue.iVStr).apply()
    }

    fun putLong(key: String, value: Long) {
        val encryptedValue: EncryptedData? =
            cryptoProvider?.encrypt(value.toString()) // This is for Value
        if (encryptedValue == null) {
            Log.e(TAG, "Encrypted data is null ")
            return
        }
        mPref!!.edit().putString(key, encryptedValue.encryptedStr).apply()
        mPref!!.edit().putString("$key-Iv", encryptedValue.iVStr).apply()
    }

    fun putString(key: String, value: String) {
        val encryptedValue: EncryptedData? = cryptoProvider?.encrypt(value) // This is for Value
        Log.d(TAG, "Encrypted Value of  $value = $encryptedValue")
        if (encryptedValue == null) {
            Log.e(TAG, "Encrypted data is null ")
            return
        }
        mPref!!.edit().putString(key, encryptedValue.encryptedStr).apply()
        mPref!!.edit().putString("$key-Iv", encryptedValue.iVStr).apply()
    }

    fun putFloat(key: String, value: Float) {
        val encryptedValue: EncryptedData? =
            cryptoProvider?.encrypt(value.toString()) // This is for Value
        if (encryptedValue == null) {
            Log.e(TAG, "Encrypted data is null ")
            return
        }
        mPref!!.edit().putString(key, encryptedValue.encryptedStr).apply()
        mPref!!.edit().putString("$key-Iv", encryptedValue.iVStr).apply()
    }

    fun putBool(key: String, value: Boolean) {
        val encryptedValue: EncryptedData? =
            cryptoProvider?.encrypt(value.toString()) // This is for Value
        if (encryptedValue == null) {
            Log.e(TAG, "Encrypted data is null ")
            return
        }
        mPref!!.edit().putString(key, encryptedValue.encryptedStr).apply()
        mPref!!.edit().putString("$key-Iv", encryptedValue.iVStr).apply()
    }

    fun putStringSet(key: String, value: Set<String?>) {
        val encryptedValue: EncryptedData? = cryptoProvider?.encrypt(value.toString())
        if (encryptedValue == null) {
            Log.e(TAG, "Encrypted data is null ")
            return
        }
        mPref!!.edit().putString(key, encryptedValue.encryptedStr).apply()
        mPref!!.edit().putString("$key-Iv", encryptedValue.iVStr).apply()
    }

    fun putDouble(key: String, value: Double) {
        val encryptedValue: EncryptedData? =
            cryptoProvider?.encrypt(value.toString()) // This is for Value
        if (encryptedValue == null) {
            Log.e(TAG, "Encrypted data is null ")
            return
        }
        mPref!!.edit().putString(key, encryptedValue.encryptedStr).apply()
        mPref!!.edit().putString("$key-Iv", encryptedValue.iVStr).apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        val EncryptedString = mPref!!.getString(key, defaultValue)
        if (defaultValue === EncryptedString) return defaultValue
        val Iv = mPref!!.getString("$key-Iv", null) ?: return null
        val encryptedData =
            EncryptedData(
                EncryptedString!!, Iv
            )
        //    Log.e(TAG,"Decrypted Value of  "+EncryptedString + " = "+ decryptedString);
        return cryptoProvider?.decrypt(encryptedData)
    }

    fun getInt(key: String, defaultValue: Int): Int? {
        val EncryptedString = mPref!!.getString(key, null) ?: return defaultValue
        val Iv = mPref!!.getString("$key-Iv", null) ?: return defaultValue
        val encryptedData = EncryptedData(EncryptedString, Iv)
        val decryptedString: String? = cryptoProvider?.decrypt(encryptedData)
        //    Log.e(TAG,"Decrypted Value of  "+EncryptedString + " = "+ decryptedString);
        return decryptedString?.toInt()
    }

    fun getLong(key: String, defaultValue: Long): Long? {
        val EncryptedString = mPref!!.getString(key, null) ?: return defaultValue
        val Iv = mPref!!.getString("$key-Iv", null) ?: return defaultValue
        val encryptedData = EncryptedData(EncryptedString, Iv)
        val decryptedString: String? = cryptoProvider?.decrypt(encryptedData)
        //    Log.e(TAG,"Decrypted Value of  "+EncryptedString + " = "+ decryptedString);
        return decryptedString?.toLong()
    }

    fun getFloat(key: String, defaultValue: Float): Float? {
        val EncryptedString = mPref!!.getString(key, null) ?: return defaultValue
        val Iv = mPref!!.getString("$key-Iv", null) ?: return defaultValue
        val encryptedData = EncryptedData(EncryptedString, Iv)
        val decryptedString: String? = cryptoProvider?.decrypt(encryptedData)
        //    Log.e(TAG,"Decrypted Value of  "+EncryptedString + " = "+ decryptedString);
        return decryptedString?.toFloat()
    }

    fun getBool(key: String, defaultValue: Boolean): Boolean {
        val EncryptedString = mPref!!.getString(key, null)
            ?: return defaultValue //Difficult to understand the difference between default value and actual storage
        val Iv = mPref!!.getString("$key-Iv", null) ?: return defaultValue
        //Difficult to understand the difference between default value and actual storage
        val encryptedData = EncryptedData(EncryptedString, Iv)
        val decryptedString: String? = cryptoProvider?.decrypt(encryptedData)
        //     Log.e(TAG,"Decrypted Value of  "+EncryptedString + " = "+ decryptedString);
        return java.lang.Boolean.parseBoolean(decryptedString)
    }

    fun getStringSet(key: String, defaultValue: Set<String?>?): Set<String?>? {
        val EncryptedString = mPref!!.getString(key, null) ?: return defaultValue
        val Iv = mPref!!.getString("$key-Iv", null) ?: return defaultValue
        val encryptedData = EncryptedData(EncryptedString, Iv)
        val decryptedString: String? = cryptoProvider?.decrypt(encryptedData)
        //     Log.e(TAG,"Decrypted Value of  "+EncryptedString + " = "+ decryptedString);
        val set: MutableSet<String?> = HashSet()
        set.add(decryptedString)
        return set
    }

    fun getDouble(key: String, defaultValue: Double): Double? {
        val EncryptedString = mPref!!.getString(key, null) ?: return defaultValue
        val Iv = mPref!!.getString("$key-Iv", null) ?: return defaultValue
        val encryptedData = EncryptedData(EncryptedString, Iv)
        val decryptedString: String? = cryptoProvider?.decrypt(encryptedData)
        //   Log.e(TAG,"Decrypted Value of  "+EncryptedString + " = "+ decryptedString);
        return decryptedString?.toDouble()
    }

    operator fun contains(key: String?): Boolean {
        return mPref!!.contains(key)
    }

    fun removeKey(key: String?) {
        mPref!!.edit().remove(key)
        mPref!!.edit().apply()
    }

    fun clearStorage() {
        mPref!!.edit().clear()
        mPref!!.edit().apply()
    }
}