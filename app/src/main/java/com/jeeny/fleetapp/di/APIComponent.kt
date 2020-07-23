package com.jeeny.fleetapp.di

import com.jeeny.fleetapp.netwrok.FleetApiService
import dagger.Component

@Component(modules = [APIModule::class])
interface APIComponent {
    fun inject(service: FleetApiService)
}