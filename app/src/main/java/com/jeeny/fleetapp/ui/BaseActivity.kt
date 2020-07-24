package com.jeeny.fleetapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.jeeny.fleetapp.R
import com.jeeny.fleetapp.di.ActivityComponent
import com.jeeny.fleetapp.di.ActivityModule
import com.jeeny.fleetapp.di.AppModule
import com.jeeny.fleetapp.di.DaggerActivityComponent

open class BaseActivity : AppCompatActivity() {
    lateinit var mComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mComponent = DaggerActivityComponent.builder()
            .appModule(AppModule(application))
            .activityModule(ActivityModule(this))
            .build()
    }

    fun getComponent(): ActivityComponent = mComponent

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