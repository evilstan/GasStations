package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class GetAllGasStationsUseCase(repository: Repository) {
    val stations = repository.allGasStations()
}