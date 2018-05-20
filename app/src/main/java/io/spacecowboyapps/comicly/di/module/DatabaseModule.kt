package io.spacecowboyapps.comicly.di.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import io.spacecowboyapps.comicly.data.db.ComicDao
import io.spacecowboyapps.comicly.data.db.Database
import io.spacecowboyapps.comicly.data.db.Database.Companion.DATABASE_NAME
import io.spacecowboyapps.comicly.di.ApplicationContext
import io.spacecowboyapps.comicly.di.PerApp

@Module
class DatabaseModule {

    @Provides
    @PerApp
    fun provideDatabase(@ApplicationContext context: Context): Database =
            Room.databaseBuilder(context, Database::class.java, DATABASE_NAME).build()

    @Provides
    @PerApp
    fun provideComicDao(database: Database): ComicDao = database.comicDao()
}