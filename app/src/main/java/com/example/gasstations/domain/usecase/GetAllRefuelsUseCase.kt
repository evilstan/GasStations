package com.example.gasstations.domain.usecase

import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.domain.repository.Repository

class GetAllRefuelsUseCase(private val repository: Repository) {
    suspend fun execute() = repository.refuels().map { map(it) }

    private fun map(refuelCache: RefuelCache) =
        RefuelDomain(
            refuelCache.brand,
            refuelCache.latitude,
            refuelCache.longitude,
            refuelCache.fuelType,
            refuelCache.fuelAmount,
            refuelCache.fuelPrice,
            refuelCache.id
        )
}