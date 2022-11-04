package com.amitnadiger.myinvestment.securityProvider

class SecurityConstants {
    companion object {
        const val TRANSFORMATION_SYMMETRIC:String = "AES/CBC/PKCS7Padding"
        const val TRANSFORMATION_GCM = "AES/GCM/NoPadding"
        const val TRANSFORMATION_ASYMMETRIC:String = "RSA/ECB/PKCS1Padding"
        const val ANDROID_KEYSTORE:String =  "AndroidKeyStore"
        const val MASTER_KEY = "MASTER_SYMMETRIC_KEY"
        const val ALGORITHM_AES = "AES" // symmetric algorithm
        const val DATASTORE_UNSECURE_INTERNAL = "DATASTORE_UNSECURE_INTERNAL"
        const val KEY_ENCRYPTED_SYM_KEY = "encryptedSymmetricKey"
    }
}