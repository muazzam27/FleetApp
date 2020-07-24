package com.jeeny.fleetapp.di

import com.jeeny.fleetapp.FleetVehicleViewModel
import com.jeeny.fleetapp.MapsActivity
import com.jeeny.fleetapp.vehicles.CustomMarkerWindowInfo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class,AppModule::class,ActivityModule::class])
interface ActivityComponent {

    fun inject(fleetVehicleViewModel: FleetVehicleViewModel)
    fun inject(mapsActivity: MapsActivity)
    fun inject(customMarkerWindowInfo: CustomMarkerWindowInfo)
}