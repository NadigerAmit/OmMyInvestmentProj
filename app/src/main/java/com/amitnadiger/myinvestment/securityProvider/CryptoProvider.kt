package com.amitnadiger.myinvestment.securityProvider


import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.keystore.KeyInfo
import android.util.Log

import com.amitnadiger.myinvestment.securityProvider.SecurityConstants.Companion.ANDROID_KEYSTORE
import com.amitnadiger.myinvestment.securityProvider.SecurityConstants.Companion.MASTER_KEY
import com.amitnadiger.myinvestment.securityProvider.SecurityConstants.Companion.TRANSFORMATION_SYMMETRIC
import com.amitnadiger.myinvestment.securityProvider.SecurityConstants.Companion.ALGORITHM_AES
import com.amitnadiger.myinvestment.securityProvider.SecurityConstants.Companion.DATASTORE_UNSECURE_INTERNAL
import com.amitnadiger.myinvestment.securityProvider.SecurityConstants.Companion.KEY_ENCRYPTED_SYM_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import java.security.Key
import java.security.KeyPair
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory


class CryptoProvider(val context:Context ):ICryptoProvider {
    private val TAG = "CryptoProvider"
    private val dataStorageManager = DataStoreHolder.getDataStoreProvider(context,
       DATASTORE_UNSECURE_INTERNAL,
       false)

    init {
        Log.d(TAG, "CryptoService Cons")
        createMasterKey(null)
    }

    private fun createMasterKey(passwd: String?) {
        //Log.d(TAG, "createMasterKey ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            createAndroidSymmetricKey() // Android provided symmetric key
        } else {
            createDefaultSymmetricKey() // Java provided symmetric key
        }
    }

    private fun removeMasterKey() {
        KeyStoreProvider.removeAndroidKeyStoreKey(MASTER_KEY)
    }

    override fun encrypt(data: String): EncryptedData? {
        //Log.d(TAG, "Encrypt ")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            encryptWithAndroidSymmetricKey(data) // Symmetric algo
        } else {
            encryptWithDefaultSymmetricKey(data) // Hybrid algo ( Java symmetric + Android Asymmetric key pair  )
        }
    }

    override fun decrypt(encryptedData: EncryptedData): String? {
        Log.d(TAG, "decrypt ")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decryptWithAndroidSymmetricKey(encryptedData) // Symmetric algo
        } else {
            decryptWithDefaultSymmetricKey(encryptedData) // Hybrid algo ( Java symmetric + Android Asymmetric key pair  )
        }
    }

    private fun createAndroidSymmetricKey() {
      //  Log.d(TAG, "createAndroidSymmetricKey")
        if (KeyStoreProvider.getSymmetricKeyFromAndroidKeyStore(MASTER_KEY) == null) {
            KeyStoreProvider.createAndroidKeyStoreSymmetricKey(MASTER_KEY) as Key
        }
    }

    @TargetApi(23)
    private fun getKeyInfo(): KeyInfo? {
        var masterKey: SecretKey? = null
        var keyInfo: KeyInfo? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            masterKey = KeyStoreProvider.getSymmetricKeyFromAndroidKeyStore(
                MASTER_KEY) // Android provided symmetric key
        } else {
            // get private key
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val factory: SecretKeyFactory =
                    SecretKeyFactory.getInstance(masterKey?.algorithm, ANDROID_KEYSTORE)
                keyInfo = factory.getKeySpec(masterKey, KeyInfo::class.java) as KeyInfo
            } catch (e: Exception) {  //
                e.message?.let { Log.d(TAG, it) }
            }
        }
        return keyInfo
    }

    @TargetApi(23)
    private fun ShowKeyInfo() {
        Log.d(
            TAG,
            "********************************************KeyIno Start ********************************************"
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val keyInfo: KeyInfo? = getKeyInfo()
            Log.d(TAG, keyInfo.toString())
            Log.d(TAG, "KeySize = " + keyInfo?.keySize)
            Log.d(TAG, "KeystoreAlias = " + keyInfo?.keystoreAlias)
            Log.d(TAG, "getOrigion = " + keyInfo?.origin)
            Log.d(TAG, "getPurposes = " + keyInfo?.purposes)
            Log.d(
                TAG,
                "getUserAuthenticationValidityDurationSeconds = " + keyInfo?.userAuthenticationValidityDurationSeconds
            )
            Log.d(TAG, "isInsideSecureHardware =  " + keyInfo?.isInsideSecureHardware)
            Log.d(
                TAG,
                "isInvalidatedByBiometricEnrollment = " + keyInfo?.isInvalidatedByBiometricEnrollment
            )
            //Log.d(TAG, "isTrustedUserPresenceRequired" + keyInfo.isTrustedUserPresenceRequired()); // required API level 28.
            Log.d(TAG, "isUserAuthenticationRequired = " + keyInfo?.isUserAuthenticationRequired)
            Log.d(
                TAG,
                "isUserAuthenticationValidWhileOnBody = " + keyInfo?.isUserAuthenticationValidWhileOnBody
            )
            //Log.d(TAG, "isUserConfirmationRequired" + keyInfo.isUserConfirmationRequired()); // required API level 28.
            Log.d(
                TAG,
                "********************************************KeyIno Ends ********************************************"
            )
        }
    }


    //( Java symmetric Key + Android Asymmetric key pair  )
    private fun createDefaultSymmetricKey() {
        Log.d(TAG, "createDefaultSymmetricKey")
        var encryptedSymmetricKey: String? = null

        runBlocking {
            encryptedSymmetricKey = dataStorageManager.getString(KEY_ENCRYPTED_SYM_KEY).first()
        }
        if (encryptedSymmetricKey != null) {
            //Log.d(TAG, " Encrypted symmetric already created $encryptedSymmetricKey")
            return
        }
        val symmetricKey: SecretKey? =
            KeyStoreProvider.generateDefaultSymmetricKey() // symmetric key from Java default provider
        val masterKey: KeyPair ?=
            KeyStoreProvider.createAndroidKeyStoreAsymmetricKeyPair(MASTER_KEY,context) // Key pair from Android keyStore
        try {
            encryptedSymmetricKey =
                SymmetricKeyWrapper().wrapKey(symmetricKey, masterKey?.public)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if( encryptedSymmetricKey != null) {
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                dataStorageManager.putString(KEY_ENCRYPTED_SYM_KEY, encryptedSymmetricKey!!)
            }
        }
    }

    private fun encryptWithAndroidSymmetricKey(data: String): EncryptedData? {
       // Log.d(TAG, "encryptWithAndroidSymmetricKey")
        val symmetricKey: SecretKey = KeyStoreProvider.getSymmetricKeyFromAndroidKeyStore(MASTER_KEY)
            ?: return null
        var cipherObj: EncrypterDecrypter? = null
        try {
            cipherObj = EncrypterDecrypter(context, TRANSFORMATION_SYMMETRIC)
            return cipherObj.encrypt(data, symmetricKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun decryptWithAndroidSymmetricKey(encryptedData: EncryptedData): String? {
       // Log.d(TAG, "decryptWithAndroidSymmetricKey")
        val symmetricKey: SecretKey = KeyStoreProvider.getSymmetricKeyFromAndroidKeyStore(MASTER_KEY)
            ?: return null
        var cipherObj: EncrypterDecrypter? = null
        try {
            cipherObj = EncrypterDecrypter(context, TRANSFORMATION_SYMMETRIC)
            return cipherObj.decrypt(
                encryptedData.encryptedStr,
                symmetricKey,
                encryptedData.iVStr
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun encryptWithDefaultSymmetricKey(data: String): EncryptedData? {
       // Log.d(TAG, "encryptWithDefaultSymmetricKey ")
        val asymmetricKeyPair: KeyPair? = KeyStoreProvider.getAsymmetricKeyPairFromAndroidKeyStore(
            MASTER_KEY) // get the asymmetric key.
        var encryptedSymmetricMasterKey:String? = null

        runBlocking {
            encryptedSymmetricMasterKey = dataStorageManager.getString(KEY_ENCRYPTED_SYM_KEY).first()
        }
        var cipherObj: EncrypterDecrypter? = null
        if(encryptedSymmetricMasterKey == null) return null
        try {
            val symmetricKey: SecretKey = SymmetricKeyWrapper().unWrapKey(
                encryptedSymmetricMasterKey,  // unWrapKey is similar to decrypting the encrypted symmetric key by  Private Key
                ALGORITHM_AES,  // wrappedKeyAlgorithm is the algorithm associated with the wrapped key
                Cipher.SECRET_KEY,  // Wrapped Key
                asymmetricKeyPair?.private
            ) as SecretKey // unWrap the key with
            cipherObj = EncrypterDecrypter(context, TRANSFORMATION_SYMMETRIC)
            return cipherObj.encrypt(data, symmetricKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun decryptWithDefaultSymmetricKey(encryptedData: EncryptedData): String? {
       // Log.d(TAG, "decryptWithDefaultSymmetricKey ")
        val asymmetricKeyPair: KeyPair ?=
            KeyStoreProvider.getAsymmetricKeyPairFromAndroidKeyStore(MASTER_KEY)
        var encryptedSymmetricMasterKey:String? =null
        runBlocking {
            encryptedSymmetricMasterKey = dataStorageManager.getString(KEY_ENCRYPTED_SYM_KEY).first()
        }
        var cipherObj: EncrypterDecrypter? = null
        try {
            val symmetricKey: SecretKey = SymmetricKeyWrapper().unWrapKey(
                encryptedSymmetricMasterKey,  // unWrapKey is similar to decrypting the encrypted symmetric key by  Private Key
                ALGORITHM_AES,  // wrappedKeyAlgorithm is the algorithm associated with the wrapped key
                Cipher.SECRET_KEY,  // Wrapped Key
                asymmetricKeyPair?.private
            ) as SecretKey // unWrapp the key with
            cipherObj = EncrypterDecrypter(context, TRANSFORMATION_SYMMETRIC)
            return cipherObj.decrypt(
                encryptedData.encryptedStr,
                symmetricKey,
                encryptedData.iVStr
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}