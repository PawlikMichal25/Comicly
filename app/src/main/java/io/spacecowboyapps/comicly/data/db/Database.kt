package io.spacecowboyapps.comicly.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = [Comic::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun comicDao(): ComicDao

    companion object {
        const val DATABASE_NAME = "ComiclyDatabase"
    }
}