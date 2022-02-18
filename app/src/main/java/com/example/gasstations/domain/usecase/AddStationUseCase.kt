package com.example.gasstations.domain.usecase

import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.domain.repository.Repository

class AddStationUseCase(private val repository: Repository) {

    suspend fun execute(refuelDomain: RefuelDomain) {
        repository.insert(map(refuelDomain))
    }

    private fun map(refuelCache: RefuelDomain) =
        RefuelCache(
            refuelCache.name,
            refuelCache.address,
            refuelCache.fuelAmount
        )
}