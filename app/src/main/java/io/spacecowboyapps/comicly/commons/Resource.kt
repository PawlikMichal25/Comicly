package io.spacecowboyapps.comicly.commons

import io.spacecowboyapps.comicly.commons.Status.ERROR
import io.spacecowboyapps.comicly.commons.Status.LOADING
import io.spacecowboyapps.comicly.commons.Status.SUCCESS

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(message: String?): Resource<T> {
            return Resource(ERROR, null, message)
        }
    }
}