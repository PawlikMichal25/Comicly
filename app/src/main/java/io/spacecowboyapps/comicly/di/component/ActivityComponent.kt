package io.spacecowboyapps.comicly.di.component

import dagger.Subcomponent
import io.spacecowboyapps.comicly.di.PerActivity
import io.spacecowboyapps.comicly.di.module.ActivityModule
import io.spacecowboyapps.comicly.ui.comic.ComicsActivity

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(comicsActivity: ComicsActivity)
}