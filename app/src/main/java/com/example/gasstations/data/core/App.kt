package com.example.gasstations.data.core

import android.app.Application
import android.content.Intent
import android.os.Build
import com.example.gasstations.presentation.RefuelsSyncService

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        val intent = Intent(this, RefuelsSyncService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    companion object {
        lateinit var instance: Application
            private set
    }
}