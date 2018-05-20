package io.spacecowboyapps.comicly.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.spacecowboyapps.comicly.data.preferences.AppPreferences
import io.spacecowboyapps.comicly.data.preferences.Preferences
import io.spacecowboyapps.comicly.di.ApplicationContext
import io.spacecowboyapps.comicly.di.PerApp

@Module
class PreferencesModule {

    @Provides
    @PerApp
    fun providePreferences(@ApplicationContext context: Context): Preferences =
            AppPreferences(context)
}