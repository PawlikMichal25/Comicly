package io.spacecowboyapps.comicly.di.component

import dagger.Component
import io.spacecowboyapps.comicly.di.PerActivity
import io.spacecowboyapps.comicly.di.module.ActivityModule
import io.spacecowboyapps.comicly.ui.comic.ComicsActivity

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(comicsActivity: ComicsActivity)
}