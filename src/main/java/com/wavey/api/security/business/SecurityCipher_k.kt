package com.wavey.api.security.business

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object SecurityCipher_k {
    private const val KEYVALUE = "moiatsupermnogotaenkliuch"
    private lateinit var secretKey: SecretKeySpec
    private lateinit var key: ByteArray

    fun setKey() {
        val sha: MessageDigest
        try {
            key = KEYVALUE.toByteArray(StandardCharsets.UTF_8)
            sha = MessageDigest.getInstance("SHA-256")
            key = sha.digest(key)
            key = Arrays.copyOf(key, 16)
            secretKey = SecretKeySpec(key, "AES")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    fun encrypt(strToEncrypt: String?): String? {
        if (strToEncrypt == null) return null
        try {
            setKey()
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.toByteArray(StandardCharsets.UTF_8)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun decrypt(strToDecrypt: String?): String? {
        if (strToDecrypt == null) return null
        try {
            setKey()
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            return String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
