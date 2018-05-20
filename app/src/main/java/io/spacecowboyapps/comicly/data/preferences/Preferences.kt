package io.spacecowboyapps.comicly.data.preferences

interface Preferences {

    fun getLastUpdate(): Long

    fun putLastUpdate(timestamp: Long)
}