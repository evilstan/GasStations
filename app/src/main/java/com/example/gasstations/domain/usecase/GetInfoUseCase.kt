package com.example.gasstations.domain.usecase

import com.example.gasstations.domain.models.InfoDomain
import com.example.gasstations.domain.repository.Repository

class GetInfoUseCase(private val repository: Repository) {
    fun execute(address:String):InfoDomain {
        return InfoDomain(repository.refuels(address))
    }
}