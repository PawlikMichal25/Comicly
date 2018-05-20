package io.spacecowboyapps.comicly.commons

import android.arch.lifecycle.LiveData

class AbsentLiveData<T : Any?> : LiveData<T>() {

    init {
        value = null
    }
}