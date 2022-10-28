package com.amitnadiger.myinvestment.securityProvider

import android.util.Base64
import com.amitnadiger.myinvestment.securityProvider.SecurityConstants.Companion.TRANSFORMATION_ASYMMETRIC
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import java.security.Key;
import javax.crypto.NoSuchPaddingException

class SymmetricKeyWrapper {
    private val TAG = "KeyWrapper"
    var mCipher: Cipher = Cipher.getInstance(TRANSFORMATION_ASYMMETRIC)


    fun wrapKey(keyToBeWrapped: Key?, keyToWrapWith: Key?): String? {
        try {
            mCipher.init(Cipher.WRAP_MODE, keyToWrapWith) // keyToWrapWith = public key
            val decodedData: ByteArray =
                mCipher.wrap(keyToBeWrapped) // keyToBeWrapped = plan secret(Master) key is encrypted using public key
            return Base64.encodeToString(decodedData, Base64.DEFAULT) //
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun unWrapKey(
        wrappedKeyData: String?,
        algorithm: String?,
        wrappedKeyType: Int,
        keyToUnWrapWith: Key?
    ): Key? {
        try {
            val encryptedKeyData: ByteArray = Base64.decode(
                wrappedKeyData,
                Base64.DEFAULT
            ) // encrypted secret Key converted to byte
            mCipher.init(Cipher.UNWRAP_MODE, keyToUnWrapWith) //keyToUnWrapWith = private key
            return mCipher.unwrap(
                encryptedKeyData,
                algorithm,
                wrappedKeyType
            ) // Decrypting the encrypted  secret key with private key.
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}