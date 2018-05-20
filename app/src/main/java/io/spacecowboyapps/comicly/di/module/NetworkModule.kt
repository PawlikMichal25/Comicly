package io.spacecowboyapps.comicly.di.module

import android.content.Context
import com.google.gson.Gson
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.spacecowboyapps.comicly.commons.TimeProvider
import io.spacecowboyapps.comicly.data.network.MarvelClient
import io.spacecowboyapps.comicly.data.network.MarvelClientAdapter
import io.spacecowboyapps.comicly.di.ApplicationContext
import io.spacecowboyapps.comicly.di.BaseUrl
import io.spacecowboyapps.comicly.di.PerApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = "https://gateway.marvel.com/v1/public/"

    @Provides
    @PerApp
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    @PerApp
    fun provideGson(): Gson = Gson()

    @Provides
    @PerApp
    fun providePicasso(@ApplicationContext context: Context, httpClient: OkHttpClient): Picasso =
        Picasso.Builder(context)
            .downloader(OkHttp3Downloader(httpClient))
            .build()
            .apply { Picasso.setSingletonInstance(this) }

    @Provides
    @PerApp
    fun provideRetrofit(@BaseUrl baseUrl: String, gson: Gson, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @PerApp
    fun provideMarvelClientAdapter(retrofit: Retrofit, timeProvider: TimeProvider): MarvelClientAdapter
            = MarvelClientAdapter(retrofit.create(MarvelClient::class.java), timeProvider)
}