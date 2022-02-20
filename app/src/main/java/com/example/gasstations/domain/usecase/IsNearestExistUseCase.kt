package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository
import com.google.android.gms.maps.model.LatLng

class IsNearestExistUseCase(private val repository: Repository) {
    suspend fun execute(stationPosition: LatLng) :Boolean {
        return !repository.nearest(stationPosition.latitude, stationPosition.longitude).isNullOrEmpty()
    }
}