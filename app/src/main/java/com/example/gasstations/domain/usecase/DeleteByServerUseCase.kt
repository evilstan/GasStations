package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class DeleteByServerUseCase(private val repository: Repository) {
    suspend fun execute(id: Long) =
        repository.delete(id)
}