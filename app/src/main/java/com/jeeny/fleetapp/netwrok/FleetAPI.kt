package com.jeeny.fleetapp.netwrok

import com.jeeny.fleetapp.model.FleetVehiclesResponse
import io.reactivex.Single
import retrofit2.http.GET

interface FleetAPI {

    @GET("raw/SwFd1znM")
    fun getVehicles():Single<FleetVehiclesResponse>

}