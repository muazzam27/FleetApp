package com.jeeny.fleetapp.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.jeeny.fleetapp.vehicles.CustomMarkerWindowInfo
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(val activity:AppCompatActivity) {


    @Provides
    fun provideCompositeDisposable():CompositeDisposable = CompositeDisposable()

    @Provides
    @ActivityContext
    fun provideContext():Context = activity

    @Provides
    fun provideActivity():AppCompatActivity = activity

    @Provides
    fun provideCustomWindowInfo():CustomMarkerWindowInfo = CustomMarkerWindowInfo(activity)


}