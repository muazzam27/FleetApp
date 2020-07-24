package com.jeeny.fleetapp.vehicles

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.jeeny.fleetapp.R
import com.jeeny.fleetapp.databinding.LayoutCustomWindowBinding

class CustomMarkerWindowInfo constructor(val activity: AppCompatActivity) :
    GoogleMap.InfoWindowAdapter {

    private lateinit var binding: LayoutCustomWindowBinding
    override fun getInfoContents(p0: Marker?): View {
        val view = activity.layoutInflater.inflate(R.layout.layout_custom_window, null)
        binding = LayoutCustomWindowBinding.bind(view)
        var snippet = p0?.snippet?.split(",")?.toTypedArray()
        binding.fleetType.text = "Fleet Type: ${snippet?.get(0)}"
        binding.latLong.text = "Lat: ${snippet?.get(1)} Long: ${snippet?.get(2)}"
        binding.heading.text = "Heading: ${snippet?.get(3)}"
        return view
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}