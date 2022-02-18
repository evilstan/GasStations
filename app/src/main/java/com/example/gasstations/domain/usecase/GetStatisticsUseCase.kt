package com.example.gasstations.domain.usecase

import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.models.InfoDomain
import com.example.gasstations.domain.repository.Repository

class GetStatisticsUseCase(private val repository: Repository) {

    suspend fun execute(): List<InfoDomain> {
        return calculate(repository.refuels())
    }

    private fun calculate(refuels: List<RefuelCache>) =
         refuels.groupBy { it.address }.map { map(it.value) }


    private fun map(refuels:List<RefuelCache>) =
        InfoDomain(
            refuels.first().name,
            refuels.first().address,
            refuels.size,
            refuels.sumOf { it.fuelAmount }
        )

}