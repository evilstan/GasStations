package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class NewItemsUseCase(repository: Repository) {
    val data = repository.newItems()
}