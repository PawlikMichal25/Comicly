package io.spacecowboyapps.comicly.data

import android.text.format.DateUtils
import io.reactivex.Single
import io.spacecowboyapps.comicly.commons.TimeProvider
import io.spacecowboyapps.comicly.commons.rx.AppSchedulers
import io.spacecowboyapps.comicly.data.db.Comic
import io.spacecowboyapps.comicly.data.db.ComicDao
import io.spacecowboyapps.comicly.data.network.MarvelClientAdapter
import io.spacecowboyapps.comicly.data.preferences.Preferences
import javax.inject.Inject

class ComicsRepository
@Inject constructor(
    private val clientAdapter: MarvelClientAdapter,
    private val comicDao: ComicDao,
    private val preferences: Preferences,
    private val schedulers: AppSchedulers,
    private val timeProvider: TimeProvider
) {

    fun getComics(): Single<List<Comic>> {
        return if (updateNeeded())
            loadComicsFromNetwork()
        else
            loadComicsFromDatabase()
    }

    private fun updateNeeded(): Boolean {
        return timeProvider.now() - preferences.getLastUpdate() > EXPIRATION_TIME
    }

    private fun loadComicsFromNetwork(): Single<List<Comic>> {
        return clientAdapter
            .getComics()
            .subscribeOn(schedulers.io)
            .map { it.data.results }
            .doOnSuccess {
                comicDao.deleteAll()        // To make things simpler. The policy would highly depend on app and api requirements.
                comicDao.insertAll(it)

                preferences.putLastUpdate(timeProvider.now())
            }
    }

    private fun loadComicsFromDatabase(): Single<List<Comic>> {
        return comicDao
            .getAllSingle()
            .subscribeOn(schedulers.io)
    }

    companion object {
        const val EXPIRATION_TIME = DateUtils.MINUTE_IN_MILLIS * 5
    }
}