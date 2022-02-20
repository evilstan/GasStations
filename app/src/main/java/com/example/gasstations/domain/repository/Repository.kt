package com.example.gasstations.domain.repository

import com.example.gasstations.data.storage.models.RefuelCache

interface Repository {
    suspend fun refuels(): List<RefuelCache>

    suspend fun nearest(latitude: Double, longitude: Double): List<RefuelCache>

    suspend fun insert(refuelCache: RefuelCache)

    suspend fun delete(id: Int)
}