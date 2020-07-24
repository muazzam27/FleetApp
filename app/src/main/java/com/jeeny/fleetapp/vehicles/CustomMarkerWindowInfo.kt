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
        return view
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}