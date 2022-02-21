package com.example.gasstations.domain.repository

import androidx.lifecycle.LiveData
import com.example.gasstations.data.storage.models.RefuelModel

interface Repository {
    fun refuels(): LiveData<List<RefuelModel>>

    suspend fun nearest(latitude: Double, longitude: Double): List<RefuelModel>

    suspend fun insert(refuelModel: RefuelModel)

    suspend fun delete(id: Int)
}