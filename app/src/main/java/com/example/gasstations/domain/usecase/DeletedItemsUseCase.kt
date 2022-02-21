package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class DeletedItemsUseCase (repository: Repository) {
    val data = repository.deletedItems()
}