package com.example.gasstations.domain.models

class InfoModel(
    private val refuels: List<RefuelModel>
) {
    fun name() = refuels.first().gasStationName

    fun address() = refuels.first().address

    fun visits() = refuels.size

    fun totalFuel(): Double {
        var result = 0.0
        refuels.forEach { result + it.fuelAmount }
        return result
    }
}
