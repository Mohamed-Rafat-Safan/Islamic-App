package com.example.myislamicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myislamicapp.data.model.prayers.notification.AzanPrayersUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // register prayers time in start application
        AzanPrayersUtil().registerPrayers(this)

    }
}
