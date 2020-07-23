package com.jeeny.fleetapp.di

import com.jeeny.fleetapp.FleetVehicleViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIModule::class,AppModule::class,ActivityModule::class])
interface ActivityComponent {
    fun inject(fleetVehicleViewModel: FleetVehicleViewModel)
}