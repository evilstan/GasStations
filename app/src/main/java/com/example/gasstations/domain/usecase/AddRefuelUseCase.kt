package com.example.gasstations.domain.usecase

import android.location.Location
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.repository.Repository
import com.google.android.gms.maps.model.LatLng

class AddRefuelUseCase(private val repository: Repository) {

    suspend fun execute(
        name: String,
        stationPosition: LatLng,
        fuelType: String,
        fuelVolume: Double,
        fuelPrice: Double
    ) {
        repository.insert(
            RefuelCache(
                name,
                stationPosition.latitude,
                stationPosition.longitude,
                fuelType,
                fuelVolume,
                fuelPrice
            )
        )
    }

    suspend fun execute(
        currentPosition: LatLng,
        fuelType: String,
        fuelVolume: Double,
        fuelPrice: Double
    ) {
        val nearestStations = repository.nearest(
            currentPosition.latitude,
            currentPosition.longitude
        )
        val nearestStation = findNearest(nearestStations, currentPosition)
        repository.insert(
            RefuelCache(
                nearestStation.brand,
                nearestStation.latitude,
                nearestStation.longitude,
                fuelType,
                fuelVolume,
                fuelPrice
            )
        )
    }

    private fun findNearest(nearestStations: List<RefuelCache>, currentPosition: LatLng) =
        nearestStations.minByOrNull {
            LatLng(it.latitude, it.longitude).distanceTo(currentPosition)
        }!!

    private fun LatLng.distanceTo(currentPosition: LatLng): Double {
        val distance = FloatArray(1)
        Location.distanceBetween(
            this.latitude,
            this.longitude,
            currentPosition.latitude,
            currentPosition.longitude,
            distance
        )
        return distance[0].toDouble()
    }

}