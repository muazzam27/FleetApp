package com.jeeny.fleetapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jeeny.fleetapp.databinding.ActivityMapsBinding
import com.jeeny.fleetapp.model.FleetVehiclesResponse
import com.jeeny.fleetapp.model.Poi
import com.jeeny.fleetapp.utils.Utils
import kotlinx.android.synthetic.main.activity_maps.*
import javax.inject.Inject


class MapsActivity : BaseActivity(), OnMapReadyCallback, AdapterView.OnItemSelectedListener {
    private lateinit var mMap: GoogleMap

    @Inject
    lateinit var fleetVehicleViewModel: FleetVehicleViewModel
    private lateinit var binding: ActivityMapsBinding

    private var vehicles = ArrayList<Poi>()
    private var filteredVehicles = ArrayList<Poi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getComponent().inject(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initUI()
    }

    private fun initUI() {
        try {
            supportActionBar?.hide()
            fleetVehicleViewModel = ViewModelProviders.of(this).get(FleetVehicleViewModel::class.java)
            fleetVehicleViewModel.loading.observe(this,loadingObserver)
            fleetVehicleViewModel.fleetVehicleResponse.observe(this,fleetVehicleResponseObserver)
            fleetVehicleViewModel.fleetVehiclesTypes.observe(this,fleetVehicleTypesObserver)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }


    private val fleetVehicleTypesObserver = Observer<MutableList<String>>{
        setSpinnerCategoriesData(it)
    }

    private val loadingObserver = Observer<Boolean>{
        if (it) showLoading(true) else showLoading(false)
    }

    private val fleetVehicleResponseObserver  = Observer<FleetVehiclesResponse> {
        try {
           vehicles = it.poiList as ArrayList<Poi>
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fleetVehicleViewModel.getFleetVehicles()
    }

    private fun setSpinnerCategoriesData(categories: MutableList<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(this)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        showVehiclesOnMap(fleetVehicleViewModel.getVehiclesByType(vehicles,p0?.selectedItem.toString()))
    }

    private fun showVehiclesOnMap(vehicles:ArrayList<Poi>){
        if (vehicles.size>0){
            mMap.clear()
            for (vehicle in vehicles){
                addMarker(vehicle)
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(vehicles.get(0).coordinate.latitude,vehicles.get(0).coordinate.longitude), 11f))
        }

    }
    private fun addMarker(vehicle:Poi) {
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(vehicle.coordinate.latitude,vehicle.coordinate.longitude))
                .title(vehicle.fleetType)
                .icon(BitmapDescriptorFactory.fromBitmap(Utils.getIcon(this)))

        ).showInfoWindow()
    }


}