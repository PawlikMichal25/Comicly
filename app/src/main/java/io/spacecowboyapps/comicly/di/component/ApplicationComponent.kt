package io.spacecowboyapps.comicly.di.component

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Component
import io.spacecowboyapps.comicly.ComiclyApplication
import io.spacecowboyapps.comicly.commons.rx.AppSchedulers
import io.spacecowboyapps.comicly.data.ComicsRepository
import io.spacecowboyapps.comicly.di.ApplicationContext
import io.spacecowboyapps.comicly.di.PerApp
import io.spacecowboyapps.comicly.di.module.ApplicationModule
import io.spacecowboyapps.comicly.di.module.DataModule

@PerApp
@Component(modules = [ApplicationModule::class, DataModule::class])
interface ApplicationComponent {

    fun inject(application: ComiclyApplication)

    @ApplicationContext
    fun context(): Context

    fun picasso(): Picasso

    fun schedulers(): AppSchedulers

    fun comicsRepository(): ComicsRepository
}