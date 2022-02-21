package com.example.gasstations.domain.usecase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.gasstations.domain.asFlow
import com.example.gasstations.domain.repository.Repository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IsNearestExistUseCase(private val repository: Repository) {

    suspend fun execute(stationPosition: LatLng) :Boolean {
        return repository.nearest(stationPosition.latitude, stationPosition.longitude).isNotEmpty()
    }
}