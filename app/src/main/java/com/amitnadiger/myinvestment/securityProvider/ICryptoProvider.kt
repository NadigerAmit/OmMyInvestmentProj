package com.amitnadiger.myinvestment.securityProvider

interface ICryptoProvider {
    fun encrypt(data: String): EncryptedData?
    fun decrypt(encryptedData: EncryptedData): String?
}