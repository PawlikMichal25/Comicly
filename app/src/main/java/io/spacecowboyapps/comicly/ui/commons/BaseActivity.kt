package io.spacecowboyapps.comicly.ui.commons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.spacecowboyapps.comicly.ComiclyApplication
import io.spacecowboyapps.comicly.di.component.ActivityComponent
import io.spacecowboyapps.comicly.di.component.DaggerActivityComponent

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger()
        super.onCreate(savedInstanceState)
    }

    private fun initDagger() {
        activityComponent = DaggerActivityComponent
            .builder()
            .applicationComponent((application as ComiclyApplication).getComponent())
            .build()

        injectActivity(activityComponent)
    }

    abstract fun injectActivity(component: ActivityComponent)
}