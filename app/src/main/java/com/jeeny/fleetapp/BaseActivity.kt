package com.jeeny.fleetapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showLoading(show: Boolean) {
        try {
            var containter = findViewById<LinearLayout>(R.id.container)
            if (show) {
                if (containter != null) {
                    containter.bringToFront()
                    containter.setVisibility(View.VISIBLE)
                }
            } else {
                if (containter != null) {
                    containter.setVisibility(View.GONE)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}