package io.spacecowboyapps.comicly.commons.rx

import io.reactivex.Scheduler

class AppSchedulers(
        val main: Scheduler,
        val computation: Scheduler,
        val io: Scheduler)