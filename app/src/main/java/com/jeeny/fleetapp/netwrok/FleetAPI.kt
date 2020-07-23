package com.jeeny.fleetapp.netwrok

import retrofit2.http.GET

interface FleetAPI {

    @GET("aw/SwFd1znM")
    fun getVehicles()

}