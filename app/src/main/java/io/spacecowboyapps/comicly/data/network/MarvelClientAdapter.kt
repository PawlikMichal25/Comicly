package io.spacecowboyapps.comicly.data.network

import android.util.Log
import io.reactivex.Single
import io.spacecowboyapps.comicly.BuildConfig
import io.spacecowboyapps.comicly.commons.TimeProvider
import io.spacecowboyapps.comicly.data.db.Comic
import io.spacecowboyapps.comicly.data.network.model.DataWrapper
import io.spacecowboyapps.comicly.testing.OpenForTesting
import java.lang.StringBuilder
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

@OpenForTesting
class MarvelClientAdapter
@Inject constructor(
        private val client: MarvelClient,
        private val timeProvider: TimeProvider) {

    fun getComics(): Single<DataWrapper<Comic>> {
        val timestamp = timeProvider.now()
        return client.getComics(timestamp, BuildConfig.PUBLIC_KEY, buildMd5AuthParameter(timestamp))
    }

    private fun buildMd5AuthParameter(timestamp: Long): String {
        return try {
            val md = MessageDigest.getInstance("MD5")
            val messageDigest = md.digest("$timestamp${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}".toByteArray())
            val number = BigInteger(1, messageDigest)

            val md5 = StringBuilder(number.toString(16))
            while (md5.length < 32)
                md5.append("0$md5")

            md5.toString()
        } catch (e: NoSuchAlgorithmException) {
            Log.e("DataManager", "Error hashing required parameters: " + e.message)
            ""
        }
    }
}