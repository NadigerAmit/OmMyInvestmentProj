package com.amitnadiger.myinvestment.securityProvider

import android.content.Context
import android.util.Base64.*
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class EncrypterDecrypter(val context: Context, private val transformation: String) {
    

    /**
     * This class wraps [Java Cipher] class apis with some additional possibilities.
     * Main logic of encryption and decryption happens here
     */
    private val TAG = "EncrypterDecrypter"
    private lateinit var mIv: ByteArray

    private var cipher: Cipher = Cipher.getInstance(transformation)


    fun encrypt(data: String, key: Key): EncryptedData? {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key)
            Log.e(TAG, "Key in encryptor = $key")
            val ivParams: IvParameterSpec =
                cipher.parameters.getParameterSpec(IvParameterSpec::class.java)
            mIv = ivParams.iv
            val bytes: ByteArray = cipher.doFinal(data.toByteArray())
            Log.e(TAG, "In Encryptor IV = " + Arrays.toString(ivParams.iv))
            return EncryptedData(encodeToString(bytes, DEFAULT),
                Arrays.toString(ivParams.iv)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun decrypt(data: String, key: Key, Iv: String?): String? {
        val array: ByteArray
        Log.e(TAG, "Key in decrypter = $key")
        var ivParams: IvParameterSpec? = null // This class specifies an initialization vector (IV).
        if (Iv != null) {
            val split = Iv.substring(1, Iv.length - 1).split(", ").toTypedArray()
            array = ByteArray(split.size)
            for (i in split.indices) {
                array[i] = split[i].toByte()
                ivParams =
                    IvParameterSpec(array) // Creates an IvParameterSpec object using the bytes in Iv,got during encryption.
            }
        }
        try {
            Log.e(TAG, " EncryptedString : $data")
            Log.e(TAG, "In Decrypt IV = $Iv")
            cipher.init(Cipher.DECRYPT_MODE, key, ivParams)
            val encryptedData: ByteArray = decode(data, DEFAULT)
            val decodedData: ByteArray = cipher.doFinal(encryptedData) // Actual Decryption
            val str = String(decodedData, StandardCharsets.UTF_8)
            Log.e(TAG, " Plan Text : $str")
            return str
        } catch (e: Exception) {
            when (e) {
                is InvalidAlgorithmParameterException,
                is InvalidKeyException -> {
                    Log.e(TAG, " InvalidAlgorithmParameterException, :InvalidKeyException e=  " + e.message)
                }
                else -> throw e
            }
            e.printStackTrace()
            Log.e(TAG, " Plan Text : e=  " + e.message)
        }
        return null
    }
}