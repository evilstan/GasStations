package com.example.gasstations.domain.usecase

import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.repository.Repository

class UpdateRefuelUseCase(private val repository: Repository) {
    suspend fun execute(refuelCache: RefuelCache) {
        repository.update(refuelCache)
    }
}