package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class GetDeletedItemsUseCase (repository: Repository) {
    val data = repository.deletedRefuels()
}