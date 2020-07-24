package com.jeeny.fleetapp.netwrok

import android.app.Application
import com.jeeny.fleetapp.di.APIModule
import com.jeeny.fleetapp.di.AppModule
import com.jeeny.fleetapp.di.DaggerAPIComponent
import com.jeeny.fleetapp.model.FleetVehiclesResponse
import io.reactivex.Single
import javax.inject.Inject

class FleetApiService(val api: FleetAPI) {

    fun getVehiclesList(): Single<FleetVehiclesResponse> {
        return api.getVehicles()
    }
}