package com.example.gasstations.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import com.example.gasstations.R
import com.example.gasstations.data.core.App
import com.example.gasstations.data.repository.RepositoryImpl
import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.data.storage.models.RefuelFirebaseModel
import com.example.gasstations.data.storage.models.RefuelModel
import com.example.gasstations.domain.usecase.SyncWithFirebaseUseCase
import com.example.gasstations.domain.usecase.UpdateRefuelUseCase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RefuelsSyncService() :
    LifecycleService() {
    private lateinit var liveData: LiveData<List<RefuelModel>>

    private val repository = RepositoryImpl(AppDatabase.getInstance(App.instance))
    private val syncWithFirebaseUseCase = SyncWithFirebaseUseCase(repository)
    private val updateRefuelUseCase = UpdateRefuelUseCase(repository)

    private val myRef = Firebase.database(
        "https://gas-stations-775de-default-rtdb.europe-west1.firebasedatabase.app"
    ).getReference("refuels")


    override fun onCreate() {
        val connectionManager = InternetConnection(applicationContext)
        super.onCreate()
        liveData = syncWithFirebaseUseCase.data
        liveData.observe(this) { refuels ->
                connectionManager.listen(object : InternetConnection.ConnectionListener {
                    override fun updateConnected(connected: Boolean) {
                        if (connected) {
                            if (refuels.isNotEmpty()){
                                refuels.forEach { synchronize(it) }
                            }
                        }
                    }
                })
        }
    }


    private fun synchronize(refuelModel: RefuelModel) {
        if (refuelModel.deleted){
            myRef.child("refuels").child(refuelModel.id.toString()).removeValue()
        }else if (!refuelModel.uploaded) {
            myRef.child("refuels").child(refuelModel.id.toString()).setValue(map(refuelModel))
            refuelModel.uploaded = true
        }
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                updateRefuelUseCase.execute(refuelModel)
            }
        }
    }

    private fun test() {
        startForeground()

        Timer().schedule(object : TimerTask() {
            override fun run() {
                println("Running")
                //TODO try load every N minutes
            }

        }, 1000, 1000)
    }

    private fun map(refuelModel: RefuelModel) =
        RefuelFirebaseModel(
            refuelModel.brand,
            refuelModel.latitude,
            refuelModel.longitude,
            refuelModel.fuelType,
            refuelModel.fuelVolume,
            refuelModel.fuelPrice,
            refuelModel.id
        )

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        test()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForeground() {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(101, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
    }
}