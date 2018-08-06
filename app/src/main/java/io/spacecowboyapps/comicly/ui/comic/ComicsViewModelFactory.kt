package io.spacecowboyapps.comicly.ui.comic

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.spacecowboyapps.comicly.commons.rx.AppSchedulers
import io.spacecowboyapps.comicly.data.ComicsRepository
import javax.inject.Inject

class ComicsViewModelFactory
@Inject constructor(
    private val repository: ComicsRepository,
    private val schedulers: AppSchedulers
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ComicsViewModel(repository, schedulers) as T
    }
}