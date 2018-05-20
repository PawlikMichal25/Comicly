package io.spacecowboyapps.comicly.ui.comic

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.spacecowboyapps.comicly.commons.AbsentLiveData
import io.spacecowboyapps.comicly.commons.Resource
import io.spacecowboyapps.comicly.data.ComicsRepository
import io.spacecowboyapps.comicly.data.db.Comic

class ComicsViewModel(private val repository: ComicsRepository) : ViewModel() {

    private var comics: LiveData<Resource<List<Comic>>> = AbsentLiveData()

    fun getComics(): LiveData<Resource<List<Comic>>> {
        if (comics.value == null)
            comics = repository.getComics()

        return comics
    }

    override fun onCleared() {
        repository.onCleared()
    }
}