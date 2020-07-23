package com.jeeny.fleetapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jeeny.fleetapp.di.AppModule
import com.jeeny.fleetapp.di.DaggerActivityComponent
import com.jeeny.fleetapp.model.FleetVehiclesResponse
import com.jeeny.fleetapp.model.ValidationMessage
import com.jeeny.fleetapp.netwrok.FleetApiService
import com.jeeny.fleetapp.utils.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class FleetVehicleViewModel @Inject constructor(app: Application) : AndroidViewModel(app) {

    init {
        DaggerActivityComponent.builder()
            .appModule(AppModule(app))
            .build()
            .inject(this)
    }

    val fleetVehicleResponse: MutableLiveData<FleetVehiclesResponse> by lazy { MutableLiveData<FleetVehiclesResponse>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    val validationMessage = MutableLiveData<ValidationMessage>()

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var apiService: FleetApiService

    @Inject
    lateinit var app: Application


    fun getValidationMessage(): LiveData<ValidationMessage> = validationMessage

    fun getFleetVehicles() {
        loading.value = true
        compositeDisposable.add(
            apiService.getVehiclesList()
                .applySchedulers()
                .subscribe({
                    loading.value = false
                    fleetVehicleResponse.value = it
                }, {
                    validationMessage.postValue(ValidationMessage("Something went wrong while fetching vehicles"))
                    loading.value = false
                    fleetVehicleResponse.value = null
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


