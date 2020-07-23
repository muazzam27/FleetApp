package com.jeeny.fleetapp

import android.app.Application
import com.jeeny.fleetapp.di.ActivityComponent
import com.jeeny.fleetapp.di.AppModule
import com.jeeny.fleetapp.di.DaggerActivityComponent

class App : Application() {

    lateinit var mComponent: ActivityComponent

    override fun onCreate() {
        super.onCreate()
        mComponent = DaggerActivityComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getComponent(): ActivityComponent = mComponent

}