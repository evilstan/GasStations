package com.example.gasstations.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.models.RefuelDomain

interface Repository {
    suspend fun refuels(): List<RefuelCache>

    suspend fun insert(refuelCache: RefuelCache)

    suspend fun delete(id: Int)
}