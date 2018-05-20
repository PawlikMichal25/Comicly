package io.spacecowboyapps.comicly.di.module

import dagger.Module
import dagger.Provides
import io.spacecowboyapps.comicly.commons.TimeProvider
import io.spacecowboyapps.comicly.commons.rx.AppSchedulers
import io.spacecowboyapps.comicly.data.ComicsRepository
import io.spacecowboyapps.comicly.data.db.ComicDao
import io.spacecowboyapps.comicly.data.network.MarvelClientAdapter
import io.spacecowboyapps.comicly.data.preferences.Preferences
import io.spacecowboyapps.comicly.di.PerApp

@Module(includes = [DatabaseModule::class, NetworkModule::class, PreferencesModule::class])
class DataModule {

    @Provides
    @PerApp
    fun provideComicsRepository(clientAdapter: MarvelClientAdapter,
                          comicDao: ComicDao,
                          preferences: Preferences,
                          schedulers: AppSchedulers,
                          timeProvider: TimeProvider
    ): ComicsRepository = ComicsRepository(clientAdapter, comicDao, preferences, schedulers, timeProvider)
}