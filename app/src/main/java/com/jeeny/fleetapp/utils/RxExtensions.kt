package com.jeeny.fleetapp.utils

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers


fun <T> Single<T>.applySchedulers(): Single<T> {
    return observeOn(mainThread()).subscribeOn(Schedulers.io())
}


