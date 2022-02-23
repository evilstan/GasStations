package com.example.gasstations.domain.repository

import androidx.lifecycle.LiveData
import com.example.gasstations.data.storage.models.RefuelCache

interface Repository {

    suspend fun refuel(id: Long): RefuelCache

    fun allGasStations(): LiveData<List<RefuelCache>>

    fun allRefuels(): LiveData<List<RefuelCache>>

    fun newRefuels(): LiveData<List<RefuelCache>>

    fun deletedRefuels(): LiveData<List<RefuelCache>>

    suspend fun nearest(latitude: Double, longitude: Double): List<RefuelCache>

    suspend fun contains(id: Long): Boolean

    suspend fun insert(refuelCache: RefuelCache)

    suspend fun update(refuelCache: RefuelCache)

    suspend fun delete(id: Long)
}