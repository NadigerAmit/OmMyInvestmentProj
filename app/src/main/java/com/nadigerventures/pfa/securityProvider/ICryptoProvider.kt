package com.nadigerventures.pfa.securityProvider

interface ICryptoProvider {
    fun encrypt(data: String): EncryptedData?
    fun decrypt(encryptedData: EncryptedData): String?
}