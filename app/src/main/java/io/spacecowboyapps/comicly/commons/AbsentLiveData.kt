package io.spacecowboyapps.comicly.commons

import android.arch.lifecycle.MutableLiveData

class AbsentLiveData<T : Any?> : MutableLiveData<T>() {

    init {
        value = null
    }
}