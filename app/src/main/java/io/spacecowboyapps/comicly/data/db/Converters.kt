package io.spacecowboyapps.comicly.data.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import io.spacecowboyapps.comicly.data.network.model.Image

class Converters {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromImage(image: Image): String {
            return Gson().toJson(image)
        }

        @TypeConverter
        @JvmStatic
        fun fromString(json: String): Image {
            return Gson().fromJson(json, Image::class.java)
        }
    }
}