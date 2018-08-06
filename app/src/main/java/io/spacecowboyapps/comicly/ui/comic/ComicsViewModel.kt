package io.spacecowboyapps.comicly.ui.comic

import android.arch.lifecycle.LiveData
import io.spacecowboyapps.comicly.commons.AbsentLiveData
import io.spacecowboyapps.comicly.commons.Resource
import io.spacecowboyapps.comicly.commons.rx.AppSchedulers
import io.spacecowboyapps.comicly.data.ComicsRepository
import io.spacecowboyapps.comicly.data.db.Comic
import io.spacecowboyapps.comicly.ui.commons.BaseViewModel

class ComicsViewModel(
    private val repository: ComicsRepository,
    private val schedulers: AppSchedulers
) : BaseViewModel() {

    private val comics = AbsentLiveData<Resource<List<Comic>>>()

    fun getComics(): LiveData<Resource<List<Comic>>> {
        if (comics.value == null) {
            repository
                .getComics()
                .observeOn(schedulers.main)
                .doOnSubscribe {
                    comics.value = Resource.loading(emptyList())
                }.subscribe({
                    comics.value = Resource.success(it)
                }, {
                    comics.value = Resource.error(it.message)
                })
                .autoClear()
        }

        return comics
    }
}