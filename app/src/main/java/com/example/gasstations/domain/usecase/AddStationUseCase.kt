package com.example.gasstations.domain.usecase

import android.location.Location
import com.example.gasstations.data.storage.models.RefuelModel
import com.example.gasstations.domain.repository.Repository
import com.google.android.gms.maps.model.LatLng

class AddStationUseCase(private val repository: Repository) {

    suspend fun execute(
        name: String,
        stationPosition: LatLng,
        fuelType: String,
        fuelAmount: Double,
        fuelPrice: Double
    ) {
        repository.insert(
            RefuelModel(
                name,
                stationPosition.latitude,
                stationPosition.longitude,
                fuelType,
                fuelAmount,
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
            RefuelModel(
                nearestStation.brand,
                nearestStation.latitude,
                nearestStation.longitude,
                fuelType,
                fuelVolume,
                fuelPrice
            )
        )
    }

    private fun findNearest(nearestStations: List<RefuelModel>, currentPosition: LatLng) =
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