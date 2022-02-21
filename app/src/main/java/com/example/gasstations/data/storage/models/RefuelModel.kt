package com.example.gasstations.data.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refuels")
data class RefuelModel(
    var brand: String,
    var latitude: Double,
    var longitude: Double,
    var fuelType:String,
    var fuelVolume: Double,
    var fuelPrice:Double,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var uploaded: Boolean = false
)