package com.jeeny.fleetapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.LinearLayout
import com.jeeny.fleetapp.R

object Utils {

    fun getIcon(context: Context): Bitmap {
        val height = 100
        val width = 100
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.vehicle)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }
}