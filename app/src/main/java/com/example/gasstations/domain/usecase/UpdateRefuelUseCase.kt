package com.example.gasstations.domain.usecase

import com.example.gasstations.data.storage.models.RefuelModel
import com.example.gasstations.domain.repository.Repository

class UpdateRefuelUseCase(private val repository: Repository) {
    suspend fun execute(refuelModel: RefuelModel) {
        if (refuelModel.uploaded) {
            repository.update(refuelModel)
        } else if (refuelModel.deleted) {
            repository.delete(refuelModel)
        }
    }
}