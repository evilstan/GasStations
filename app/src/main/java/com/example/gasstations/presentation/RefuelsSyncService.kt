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
import com.example.gasstations.R
import com.example.gasstations.data.core.App
import com.example.gasstations.data.repository.RepositoryImpl
import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.data.storage.models.RefuelCloud
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.usecase.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
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

    private val repository = RepositoryImpl(AppDatabase.getInstance(App.instance))

    private val updateRefuelUseCase = UpdateRefuelUseCase(repository)
    private val deleteByServerUseCase = DeleteByServerUseCase(repository)
    private val addRefuelUseCase = AddRefuelUseCase(repository)

    private val newItemsLiveData = GetNewItemsUseCase(repository).data
    private val deletedItemsLiveData = GetDeletedItemsUseCase(repository).data

    private lateinit var database: DatabaseReference

    private var startTime = 0L
    private lateinit var listener: ChildEventListener

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        startTime = System.currentTimeMillis()
        showTime()
        database = Firebase
            .database(resources.getString(R.string.database_url))
            .getReference(resources.getString(R.string.database_path))

        newItemsLiveData.observe(this) { syncNew(it) }
        deletedItemsLiveData.observe(this) { syncDeleted(it) }

        listener = (object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val refuel = snapshot.getValue(RefuelCloud::class.java)
                GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        if (refuel != null) {
                            addRefuelUseCase.execute(mapToCache(refuel))
                        }
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val refuel = snapshot.getValue(RefuelCloud::class.java)
                GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        if (refuel != null) {
                            updateRefuelUseCase.execute(mapToCache(refuel))
                        }
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val refuel = snapshot.getValue(RefuelCloud::class.java)
                GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        if (refuel != null) {
                            deleteByServerUseCase.execute(refuel.id!!)
                        }
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }
        })
        database.addChildEventListener(listener)
    }

    private fun syncNew(refuels: List<RefuelCache>) {
        refuels.forEach { refuelCache ->
            if (!refuelCache.uploaded) {
                database.child(refuelCache.id.toString())
                    .setValue(mapToCloud(refuelCache))
                    .addOnSuccessListener {
                        refuelCache.uploaded = true
                        GlobalScope.launch(Dispatchers.Main) {
                            withContext(Dispatchers.IO) {
                                updateRefuelUseCase.execute(refuelCache)
                            }
                        }
                    }
            }
        }
    }

    private fun syncDeleted(refuels: List<RefuelCache>) {
        refuels.forEach {
            database.child(it.id.toString())
                .removeValue()
        }
    }

    private fun mapToCloud(refuelCache: RefuelCache) =
        RefuelCloud(
            refuelCache.brand,
            refuelCache.latitude,
            refuelCache.longitude,
            refuelCache.fuelType,
            refuelCache.fuelVolume,
            refuelCache.fuelPrice,
            refuelCache.id
        )

    private fun mapToCache(refuelCloud: RefuelCloud) =
        RefuelCache(
            refuelCloud.brand!!,
            refuelCloud.latitude!!,
            refuelCloud.longitude!!,
            refuelCloud.fuelType!!,
            refuelCloud.fuelVolume!!,
            refuelCloud.fuelPrice!!,
            refuelCloud.id!!
        )


    private fun showTime() {
        var title = ""

        InternetConnection(applicationContext)
            .listen(object : InternetConnection.ConnectionListener {
                override fun updateConnected(connected: Boolean) {
                    title = if (connected)
                        resources.getString(R.string.default_title)
                    else
                        resources.getString(R.string.waiting_title)
                }
            })

        Timer().schedule(object : TimerTask() {
            override fun run() {
                val millis = System.currentTimeMillis() - startTime
                val time = String.format(
                    TIME_FORMAT_REGEX,
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                )
                startForeground(101, notification(title, time))

            }

        }, 0, 1000)
    }

    private fun notification(
        title: String,
        text: String
    ): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(
                    resources.getString(R.string.notification_channel_id),
                    resources.getString(R.string.notification_channel_name)
                )
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

    companion object {
        private const val TIME_FORMAT_REGEX = "%02d:%02d:%02d"

    }
}