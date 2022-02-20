package com.example.gasstations.domain.usecase

import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.models.StationInfoDomain
import com.example.gasstations.domain.repository.Repository
import com.google.android.gms.maps.model.LatLng

class GetStatisticsUseCase(private val repository: Repository) {

    suspend fun execute(): List<StationInfoDomain> {
        return calculate(repository.refuels())
    }

    private fun calculate(refuels: List<RefuelCache>) =
        // refuels.groupBy { it.address }.map { map(it.value) }
        refuels.groupBy { LatLng(it.latitude, it.longitude) }.map { map(it.value) }


    private fun map(refuels: List<RefuelCache>) =
        StationInfoDomain(
            refuels.first().brand,
            //refuels.first().address,
            "" + refuels.first().latitude + ", " + refuels.first().longitude,
            refuels.size,
            refuels.sumOf { it.fuelAmount }
        )

}