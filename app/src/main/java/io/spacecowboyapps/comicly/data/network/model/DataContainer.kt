package io.spacecowboyapps.comicly.data.network.model

import com.google.gson.annotations.SerializedName

data class DataContainer<T>(
    @SerializedName("results")
    val results: List<T>
)