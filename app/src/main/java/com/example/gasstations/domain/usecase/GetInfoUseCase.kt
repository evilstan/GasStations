package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.models.InfoModel
import com.example.gasstations.domain.repository.Repository

class GetInfoUseCase(private val repository: Repository) {
    fun execute(address:String):InfoModel {
        return InfoModel(repository.refuels(address))
    }
}