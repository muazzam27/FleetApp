package com.jeeny.fleetapp.di

import com.jeeny.fleetapp.ui.FleetVehicleViewModel
import com.jeeny.fleetapp.ui.MapsActivity
import com.jeeny.fleetapp.ui.CustomMarkerWindowInfo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class,AppModule::class,ActivityModule::class])
interface ActivityComponent {

    fun inject(fleetVehicleViewModel: FleetVehicleViewModel)
    fun inject(mapsActivity: MapsActivity)
    fun inject(customMarkerWindowInfo: CustomMarkerWindowInfo)
}