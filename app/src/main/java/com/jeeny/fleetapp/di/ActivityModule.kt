package com.jeeny.fleetapp.di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule {
    @Provides
    fun provideCompositeDisposable():CompositeDisposable = CompositeDisposable()
}