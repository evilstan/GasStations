package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class GetAllRefuelsUseCase(private val repository: Repository) {
    fun execute() = repository.refuels()
}