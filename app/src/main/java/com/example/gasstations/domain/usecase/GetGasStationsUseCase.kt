package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class GetGasStationsUseCase(private val repository: Repository) {
    suspend fun execute() = repository.stations()
}