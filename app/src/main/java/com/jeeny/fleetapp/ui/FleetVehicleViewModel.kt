package com.jeeny.fleetapp.ui

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jeeny.fleetapp.di.ActivityModule
import com.jeeny.fleetapp.di.AppModule
import com.jeeny.fleetapp.di.DaggerActivityComponent
import com.jeeny.fleetapp.model.FleetVehiclesResponse
import com.jeeny.fleetapp.model.Poi
import com.jeeny.fleetapp.model.ValidationMessage
import com.jeeny.fleetapp.netwrok.FleetApiService
import com.jeeny.fleetapp.utils.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FleetVehicleViewModel @Inject constructor(app: Application) : AndroidViewModel(app) {

   // init {
//        DaggerActivityComponent.builder()
//            .appModule(AppModule(app))
//            .activityModule(ActivityModule(app))
//            .build()
//            .inject(this)
//    }

    val fleetVehicleResponse: MutableLiveData<FleetVehiclesResponse> by lazy { MutableLiveData<FleetVehiclesResponse>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    val validationMessage = MutableLiveData<ValidationMessage>()
    val fleetVehiclesTypes:MutableLiveData<MutableList<String>> by lazy { MutableLiveData<MutableList<String>>()}


    lateinit var compositeDisposable: CompositeDisposable
    lateinit var apiService: FleetApiService

    fun setObject(compositeDisposable: CompositeDisposable,apiService: FleetApiService){
        this.compositeDisposable =compositeDisposable
        this.apiService = apiService
    }



    fun getValidationMessage(): LiveData<ValidationMessage> = validationMessage


    fun getFleetVehicles() {
        loading.value = true
        compositeDisposable.add(
            apiService.getVehiclesList()
                .applySchedulers()
                .subscribe({
                    loading.value = false
                    fleetVehicleResponse.value = it
                    fleetVehiclesTypes.value = getVehiclesTypes(it.poiList)

                }, {
                    validationMessage.postValue(ValidationMessage("Something went wrong while fetching vehicles"))
                    loading.value = false
                    fleetVehicleResponse.value = null
                })
        )
    }

    fun getVehiclesByType(vehicles: List<Poi>, type: String): ArrayList<Poi> {
        var vehiclesList = ArrayList<Poi>()
        if (vehicles.size > 0) {
            for (vehicle in vehicles) {
                if (vehicle.fleetType.equals(type)) vehiclesList.add(vehicle)
            }
        }
        return vehiclesList
    }

    fun getVehiclesTypes(vehicles: List<Poi>): MutableList<String> {
        var types = mutableListOf<String>()
        if (vehicles.size > 0) {
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


