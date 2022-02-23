package com.example.gasstations.data.core

import android.app.Application
import android.content.Intent
import android.os.Build
import com.example.gasstations.presentation.RefuelsSyncService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class App : Application() {
    var  scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        val intent = Intent(this, RefuelsSyncService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        instance = this
    }



    companion object {
        lateinit var instance: Application
            private set
    }
}