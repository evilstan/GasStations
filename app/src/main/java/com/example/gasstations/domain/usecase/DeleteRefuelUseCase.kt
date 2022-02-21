package com.example.gasstations.domain.usecase

import com.example.gasstations.data.storage.models.RefuelModel
import com.example.gasstations.domain.repository.Repository

class DeleteRefuelUseCase(private val repository: Repository) {
    suspend fun execute(refuelModel: RefuelModel) {
        if (refuelModel.deleted) {
            repository.delete(refuelModel)
        } else {
            refuelModel.deleted = true
            repository.update(refuelModel)
        }
    }
}