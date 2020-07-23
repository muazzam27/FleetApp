package com.jeeny.fleetapp

import androidx.appcompat.app.AppCompatActivity
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jeeny.fleetapp.databinding.ActivityMapsBinding
import com.jeeny.fleetapp.model.FleetVehiclesResponse
import kotlinx.android.synthetic.main.activity_maps.*
import javax.inject.Inject


class MapsActivity : BaseActivity(), OnMapReadyCallback, AdapterView.OnItemSelectedListener {
    private lateinit var mMap: GoogleMap

    @Inject
    lateinit var fleetVehicleViewModel: FleetVehicleViewModel
    private lateinit var binding: ActivityMapsBinding

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
            //setSpinnerCategoriesData(arrayOf("Taxi","Pooling"))

        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private val loadingObserver = Observer<Boolean>{
        if (it) showLoading(true) else showLoading(false)
    }

    private val fleetVehicleResponseObserver  = Observer<FleetVehiclesResponse> {

        try {
            if (it!=null && it.poiList.size>0){
                //todo show the list
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        fleetVehicleViewModel.getFleetVehicles()
    }

    private fun setSpinnerCategoriesData(categories: Array<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setOnItemSelectedListener(this)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

}