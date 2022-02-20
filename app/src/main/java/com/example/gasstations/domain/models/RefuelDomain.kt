package com.example.gasstations.domain.models

data class RefuelDomain(
    val brand: String,
    var latitude: Double,
    var longitude: Double,
    var fuelType:String,
    val fuelVolume: Double,
    var fuelPrice:Double,
    val id: Int = 0
)