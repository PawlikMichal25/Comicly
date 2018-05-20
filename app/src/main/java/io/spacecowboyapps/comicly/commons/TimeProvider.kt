package io.spacecowboyapps.comicly.commons

import io.spacecowboyapps.comicly.testing.OpenForTesting

@OpenForTesting
class TimeProvider {
    fun now() = System.currentTimeMillis()
}