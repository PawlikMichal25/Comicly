package io.spacecowboyapps.comicly.data.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import io.spacecowboyapps.comicly.data.network.model.Image

@Entity
data class Comic(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("thumbnail")
    val thumbnail: Image
)