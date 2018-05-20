package io.spacecowboyapps.comicly

import android.app.Application
import io.spacecowboyapps.comicly.di.component.ApplicationComponent
import io.spacecowboyapps.comicly.di.component.DaggerApplicationComponent
import io.spacecowboyapps.comicly.di.module.ApplicationModule
import io.spacecowboyapps.comicly.testing.OpenForTesting

@OpenForTesting
class ComiclyApplication : Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        applicationComponent =
                DaggerApplicationComponent
                    .builder()
                    .applicationModule(ApplicationModule(this))
                    .build()
        applicationComponent.inject(this)
    }

    fun getComponent() = applicationComponent
}