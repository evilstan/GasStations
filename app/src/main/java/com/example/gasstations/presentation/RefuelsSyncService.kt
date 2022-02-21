package com.example.gasstations.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.lifecycle.LifecycleService
import com.example.gasstations.R
import com.example.gasstations.data.core.App
import com.example.gasstations.data.repository.RepositoryImpl
import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.data.storage.models.RefuelFirebaseModel
import com.example.gasstations.data.storage.models.RefuelModel
import com.example.gasstations.domain.usecase.DeleteRefuelUseCase
import com.example.gasstations.domain.usecase.DeletedItemsUseCase
import com.example.gasstations.domain.usecase.NewItemsUseCase
import com.example.gasstations.domain.usecase.UpdateRefuelUseCase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit

class RefuelsSyncService :
    LifecycleService() {
    private val defaultTitle = "Sync service working"
    private val waitingTitle = "Sync service working"
    private val databasePath = "refuels"
    private val databaseUrl =
        "https://gas-stations-775de-default-rtdb.europe-west1.firebasedatabase.app"

    private lateinit var connectionManager: InternetConnection
    private var title = defaultTitle

    private val repository = RepositoryImpl(AppDatabase.getInstance(App.instance))
    private val updateRefuelUseCase = UpdateRefuelUseCase(repository)
    private val deleteRefuelUseCase = DeleteRefuelUseCase(repository)

    private val newItemsLiveData = NewItemsUseCase(repository).data
    private val deletedItemsLiveData = DeletedItemsUseCase(repository).data

    private val myRef = Firebase.database(databaseUrl).getReference(databasePath)
    private lateinit var startTime: Calendar

    override fun onCreate() {
        super.onCreate()
        startTime = Calendar.getInstance()
        showTime()

        connectionManager = InternetConnection(applicationContext)

        newItemsLiveData.observe(this) { synchronize(it) }
        deletedItemsLiveData.observe(this) { synchronize(it) }
    }

    private fun synchronize(refuels: List<RefuelModel>) {
        connectionManager.listen(object : InternetConnection.ConnectionListener {
            override fun updateConnected(connected: Boolean) {
                if (connected) {
                    title = defaultTitle
                    refuels.forEach {
                        if (it.deleted) {
                            syncDeletedItems(it)
                        }
                        if (!it.uploaded) {
                            syncNewItems(it)
                        }
                    }
                } else {
                    title = waitingTitle
                }
            }
        })
    }

    private fun syncNewItems(refuelModel: RefuelModel) {
        myRef.child(databasePath).child(refuelModel.id.toString()).setValue(map(refuelModel))
            .addOnSuccessListener {
                refuelModel.uploaded = true
                GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        updateRefuelUseCase.execute(refuelModel)
                    }
                }
            }
    }

    private fun syncDeletedItems(refuelModel: RefuelModel) {
        myRef.child(databasePath).child(refuelModel.id.toString()).removeValue()
            .addOnSuccessListener {
                GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        deleteRefuelUseCase.execute(refuelModel)
                    }
                }
            }
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

    private fun showTime() {

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val millis = Calendar.getInstance().timeInMillis - startTime.timeInMillis
                val time = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)-
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );
                startForeground(101, notification(title, time))

            }

        }, 0,1000)
    }

    private fun notification(
        title: String,
        text: String
    ): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                ""
            }

        return NotificationCompat.Builder(this, channelId)
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentTitle(title)
            .setContentText(text)
            .build()
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