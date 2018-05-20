package io.spacecowboyapps.comicly.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.text.format.DateUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.spacecowboyapps.comicly.commons.Resource
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

    private val disposable = CompositeDisposable()

    fun getComics(): LiveData<Resource<List<Comic>>> {
        val liveData = MutableLiveData<Resource<List<Comic>>>()

        if (updateNeeded())
            loadComicsFromNetwork(liveData)
        else
            loadComicsFromDatabase(liveData)

        return liveData
    }

    private fun updateNeeded(): Boolean {
        return timeProvider.now() - preferences.getLastUpdate() > EXPIRATION_TIME
    }

    private fun loadComicsFromNetwork(liveData: MutableLiveData<Resource<List<Comic>>>) {
        disposable.add(clientAdapter.getComics()
            .observeOn(schedulers.io)
            .subscribeOn(schedulers.io)
            .doOnSubscribe {
                liveData.value = Resource.loading(emptyList())
            }
            .subscribe({
                val comics = it.data.results

                liveData.postValue(Resource.success(comics))

                comicDao.deleteAll()        // To make things simpler. The policy would highly depend on app and api requirements.
                comicDao.insertAll(comics)

                preferences.putLastUpdate(timeProvider.now())
            }, {
                liveData.postValue(Resource.error(it.message))
            }))
    }

    private fun loadComicsFromDatabase(liveData: MutableLiveData<Resource<List<Comic>>>) {
        disposable.add(comicDao.getAllSingle()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .doOnSubscribe {
                liveData.value = Resource.loading(emptyList())
            }
            .subscribe(Consumer {
                liveData.value = Resource.success(it)
            }))
    }

    fun onCleared(){
        disposable.dispose()
    }

    companion object {
        const val EXPIRATION_TIME = DateUtils.MINUTE_IN_MILLIS * 5
    }
}