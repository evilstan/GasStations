package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.repository.Repository

class SyncWithFirebaseUseCase(repository: Repository) {
    val data = repository.notUpdated()
}