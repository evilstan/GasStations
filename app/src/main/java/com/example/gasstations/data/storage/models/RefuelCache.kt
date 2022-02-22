package com.example.gasstations.data.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refuels")
data class RefuelCache(
    var brand: String,
    var latitude: Double,
    var longitude: Double,
    var fuelType:String,
    var fuelVolume: Double,
    var fuelPrice:Double,
    @PrimaryKey
    var id: Long,
    var uploaded: Boolean = false,
    var deleted: Boolean = false
)