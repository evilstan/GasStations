package com.example.gasstations.domain.models

class InfoDomain(
    private val refuels: List<RefuelDomain>
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