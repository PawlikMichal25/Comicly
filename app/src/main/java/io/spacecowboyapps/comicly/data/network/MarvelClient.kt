package io.spacecowboyapps.comicly.data.network

import io.reactivex.Single
import io.spacecowboyapps.comicly.data.db.Comic
import io.spacecowboyapps.comicly.data.network.model.DataWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelClient {

    @GET("comics")
    fun getComics(
        @Query("ts") timestamp: Long,
        @Query("apikey") publicKey: String,
        @Query("hash") md5Digest: String
    ): Single<DataWrapper<Comic>>
}