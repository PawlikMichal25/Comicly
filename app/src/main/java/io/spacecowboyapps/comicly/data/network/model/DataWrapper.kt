package io.spacecowboyapps.comicly.data.network.model

import com.google.gson.annotations.SerializedName

data class DataWrapper<T>(
    @SerializedName("data")
    val data: DataContainer<T>
)