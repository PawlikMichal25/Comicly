package io.spacecowboyapps.comicly.ui.commons

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {

    private val disposable = CompositeDisposable()

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    protected fun Disposable.autoClear() {
        disposable.add(this)
    }
}