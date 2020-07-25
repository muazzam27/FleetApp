package com.jeeny.fleetapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jeeny.fleetapp.model.FleetVehiclesResponse
import com.jeeny.fleetapp.model.Poi
import com.jeeny.fleetapp.netwrok.FleetApiService
import com.jeeny.fleetapp.utils.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class FleetVehicleViewModel @Inject constructor(app: Application,val apiService: FleetApiService,val compositeDisposable: CompositeDisposable) : AndroidViewModel(app) {

    val fleetVehicleResponse: MutableLiveData<FleetVehiclesResponse> by lazy { MutableLiveData<FleetVehiclesResponse>() }
    val loading =   BehaviorSubject.createDefault(false)
    val fleetVehiclesTypes:MutableLiveData<MutableList<String>> by lazy { MutableLiveData<MutableList<String>>()}


    fun getFleetVehicles() {
        loading.onNext(true)
        compositeDisposable.add(
            apiService.getVehiclesList()
                .applySchedulers()
                .subscribe({
                    loading.onNext(false)
                    fleetVehicleResponse.value = it
                    fleetVehiclesTypes.value = getVehiclesTypes(it.poiList)

                }, {
                    loading.onNext(false)
                    fleetVehicleResponse.value = null
                })
        )
    }

    fun getVehiclesByType(vehicles: List<Poi>, type: String): ArrayList<Poi> {
        var vehiclesList = ArrayList<Poi>()
        if (vehicles.isNotEmpty()) {
            for (vehicle in vehicles) {
                if (vehicle.fleetType.equals(type)) vehiclesList.add(vehicle)
            }
        }
        return vehiclesList
    }

    fun getVehiclesTypes(vehicles: List<Poi>): MutableList<String> {
        var types = mutableListOf<String>()
        if (vehicles.isNotEmpty()) {
            for (vehicle in vehicles) {
                if (!types.contains(vehicle.fleetType)) types.add(vehicle.fleetType)
            }
        }
        return types
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


