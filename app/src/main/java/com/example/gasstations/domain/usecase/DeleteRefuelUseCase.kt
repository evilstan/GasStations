package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.domain.repository.Repository

class DeleteRefuelUseCase(private val repository: Repository) {
    suspend fun execute(refuelDomain: RefuelDomain) {
        repository.delete(refuelDomain.id)
    }
}