package io.spacecowboyapps.comicly.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.spacecowboyapps.comicly.commons.TimeProvider
import io.spacecowboyapps.comicly.commons.rx.AppSchedulers
import io.spacecowboyapps.comicly.di.ApplicationContext
import io.spacecowboyapps.comicly.di.PerApp

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    @PerApp
    fun provideAppSchedulers(): AppSchedulers =
            AppSchedulers(AndroidSchedulers.mainThread(), Schedulers.computation(), Schedulers.io())

    @Provides
    fun provideTimeProvider(): TimeProvider = TimeProvider()
}