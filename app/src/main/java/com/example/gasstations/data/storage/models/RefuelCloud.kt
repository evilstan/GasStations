package com.example.gasstations.data.storage.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class RefuelCloud(
    val brand: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val fuelType:String? = null,
    val fuelVolume: Double? = null,
    val fuelPrice:Double? = null,
    val id: Long? = null
)

