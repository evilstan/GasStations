package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class GetNewItemsUseCase(repository: Repository) {
    val data = repository.newRefuels()
}