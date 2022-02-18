package com.example.gasstations.data.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "refuels")
data class RefuelCache(
    var name: String,
    var address: String,
    var fuelAmount: Double,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var uploaded: Boolean = false
) {
}