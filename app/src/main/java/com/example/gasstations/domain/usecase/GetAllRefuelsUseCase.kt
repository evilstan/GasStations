package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class GetAllRefuelsUseCase(repository: Repository) {
    val refuels = repository.allRefuels()
}