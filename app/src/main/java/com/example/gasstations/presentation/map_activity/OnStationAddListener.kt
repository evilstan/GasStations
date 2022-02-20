package com.example.gasstations.presentation.map_activity

interface OnStationAddListener {
    fun addStation(
        brand: String = "",
        fuelType: String,
        fuelVolume: Double,
        fuelPrice: Double
    )
}
interface OnRefuelAddListener {
    fun addRefuel(
        fuelType: String,
        fuelVolume: Double,
        fuelPrice: Double
    )
}