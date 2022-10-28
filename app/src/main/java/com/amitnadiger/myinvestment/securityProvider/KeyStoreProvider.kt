package com.amitnadiger.myinvestment.securityProvider

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import com.amitnadiger.myinvestment.securityProvider.SecurityConstants.Companion.ANDROID_KEYSTORE
import java.io.IOException
import java.math.BigInteger
import java.security.*
import java.security.cert.CertificateException
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.security.auth.x500.X500Principal


object KeyStoreProvider {

    private const val  TAG = "KeyStoreProvider"
    private var keyStore:KeyStore? = null

    init {
        createAndroidKeyStore()
    }


    private fun createAndroidKeyStore() {
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEYSTORE) // Get the Android key store
            Log.i(TAG, "getDefaultType Key store <-" + KeyStore.getDefaultType().toString())
            keyStore!!.load(null) // <- Create an empty keystore based on our application Id.
            Log.i(TAG, "createAndroidKeyStore <-")
        } catch (e: Exception) {
            when(e) {
                is KeyStoreException ->{
                    Log.e(TAG, "createAndroidKeyStore Exception in getInstance <-$e")
                }
                is IllegalArgumentException,
                is IOException,
                is NoSuchAlgorithmException,
                is CertificateException -> {
                    Log.e(TAG, "createAndroidKeyStore Exception in load <-$e")
                }
                else -> throw e
            }
            e.printStackTrace()
        }
    }

    /**
     * @return symmetric key from Android Key Store or null if any key with given alias exists
     */
    fun getSymmetricKeyFromAndroidKeyStore(alias: String): SecretKey? {
        var symmetricKey: Key? = null
        try {
            symmetricKey = keyStore?.getKey(alias, null)
        } catch (e: java.lang.Exception) {
            when (e) {
                is KeyStoreException,
                is NoSuchAlgorithmException,
                is UnrecoverableKeyException -> {
                    Log.e(TAG, "getAndroidKeyStoreSymmetricKey:getKey() <-$e")
                    e.printStackTrace()
                    return null
                }
                else -> {
                    e.printStackTrace()
                    throw e
                }
            }
        }
        if (symmetricKey == null) {
            return null
        }
        return symmetricKey as? SecretKey
    }

    /**
     * @return asymmetric keypair from Android Key Store or null if any key with given alias exists
     */
    fun getAsymmetricKeyPairFromAndroidKeyStore(alias: String?): KeyPair? {
        var privateKey: Key? = null
        var publicKey: Key? = null
        try {
            privateKey = keyStore?.getKey(alias, null)
            publicKey = keyStore?.getCertificate(alias)?.publicKey
            //   Log.e(TAG, "getAndroidKeyStoreAsymmetricKeyPair Pri  " + privateKey.toString());
            //   Log.e(TAG, "getAndroidKeyStoreAsymmetricKeyPair Pub  " + publicKey.toString());
        } catch (e: Exception) {
            when(e) {
                is KeyStoreException,
                is NoSuchAlgorithmException,
                is UnrecoverableKeyException -> {
                    Log.e(TAG, "getAndroidKeyStoreSymmetricKey:getKey() <-$e")
                }
                else -> throw e
            }
            e.printStackTrace()
        }
        return if (privateKey != null && publicKey != null) {
            KeyPair(publicKey as PublicKey?, privateKey as PrivateKey?)
        } else null
    }

    @TargetApi(23)
    fun createAndroidKeyStoreSymmetricKey(alias: String?): SecretKey? {
        try {
            val keyGenerator: KeyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                ANDROID_KEYSTORE
            ) // here ANDROID_KEY_STORE is provider.
            val spec = KeyGenParameterSpec.Builder(
                alias!!,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC) // Cipher block chaining , its block cipher
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7) // if block is not of correct size , need to pad.
                //.setBlockModes(KeyProperties.BLOCK_MODE_GCM) // Cipher block chaining , its block cipher
                //.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            keyGenerator.init(spec)
            return keyGenerator.generateKey()
        } catch (e: Exception) {
            return when(e) {
                is NullPointerException,
                is NoSuchAlgorithmException,
                is NoSuchProviderException,
                is IllegalArgumentException-> {
                    Log.e(TAG, "createAndroidKeyStoreSymmetricKey:KeyGenerator.getInstance() <-$e")
                    null
                }
                is InvalidAlgorithmParameterException -> {
                    Log.e(TAG, "createAndroidKeyStoreSymmetricKey:keyGenerator.init() <-$e")
                    null
                }
                else -> throw e
            }
        }
    }

    // Create symmetric key(MASTER) with default Java Providers.
    fun generateDefaultSymmetricKey(): SecretKey? {
        try {
            val keyGenerator: KeyGenerator =
                KeyGenerator.getInstance("AES") // <= Create symmetric key(MASTER) with one of default Java Providers.
            Log.e(TAG, "generateDefaultSymmetricKey ")
            return keyGenerator.generateKey()
        } catch (e: Exception) {
            when(e) {
                is NullPointerException,
                is NoSuchAlgorithmException,
                is NoSuchProviderException,
                is IllegalArgumentException-> {
                    Log.e(TAG, "createAndroidKeyStoreSymmetricKey:KeyGenerator.getInstance() <-$e")
                }
                else -> throw e
            }
            e.printStackTrace()
        }
        return null
    }

    fun removeAndroidKeyStoreKey(alias: String?) {
        try {
            keyStore?.deleteEntry(alias)
            Log.e(TAG, "removeAndroidKeyStoreKey ")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createAndroidKeyStoreAsymmetricKeyPair(alias: String,context: Context): KeyPair? {
        try {
            val generator: KeyPairGenerator = KeyPairGenerator.getInstance(
                "RSA",
                ANDROID_KEYSTORE
            ) // <--Get the KeyPairGenerator instance from keyStore of type "RSA."
            // <= This is how keystore and generated keypair are related.
            Log.e(TAG, "createAndroidKeyStoreAsymmetricKeyPair ")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                initGeneratorWithKeyGenParameterSpec(generator, alias)
            } else {
                initGeneratorWithKeyPairGeneratorSpec(generator, alias,context)
            }
            return generator.generateKeyPair()
        } catch (e: Exception) {
            when(e) {
                is NullPointerException,
                is NoSuchAlgorithmException, -> {
                    Log.e(TAG, "createAndroidKeyStoreAsymmetricKeyPair:KeyPairGenerator.getInstance() <-$e")
                }
                else -> throw e
            }
            e.printStackTrace()
        }
        return null
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun initGeneratorWithKeyGenParameterSpec(generator: KeyPairGenerator, alias: String) {
        try {
            val builder = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB) //  .setKeySize(1024)  default size is 2048
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            generator.initialize(builder.build())
            Log.e(TAG, "initGeneratorWithKeyGenParameterSpec >M")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initGeneratorWithKeyPairGeneratorSpec(
        generator: KeyPairGenerator,
        alias: String,
        context: Context
    ) {
        val startDate: Calendar = GregorianCalendar()
        val endDate: Calendar = GregorianCalendar()
        endDate.add(Calendar.YEAR, 50) // End date is 50 years later //need to check
        try {
            val builder = KeyPairGeneratorSpec.Builder(context)
                .setAlias(alias) // We 'll use the alias later to retrieve the key.  It's a key for the key!
                .setSerialNumber(BigInteger.ONE) // The serial number used for the self-signed certificate of the  generated pair.
                .setSubject(X500Principal("CN=\${alias} CA Certificate")) // The subject used for the self-signed certificate of the generated pair
                .setStartDate(startDate.time) // Date range of validity for the generated pair.
                .setEndDate(endDate.time)
            generator.initialize(builder.build())
            Log.e(TAG, "initGeneratorWithKeyPairGeneratorSpec ")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}