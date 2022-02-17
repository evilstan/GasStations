package com.example.gasstations.data.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refuels")
data class RefuelCache(
    var gasStationName: String,
    var address: String,
    var fuelAmount: Double,
    @PrimaryKey
    var id: Int = 0,
    var uploaded: Boolean = false
) {
}