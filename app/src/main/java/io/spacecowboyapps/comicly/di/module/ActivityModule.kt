package io.spacecowboyapps.comicly.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import io.spacecowboyapps.comicly.di.ActivityContext

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context = activity

    @Provides
    fun provideActivity(): AppCompatActivity = activity
}