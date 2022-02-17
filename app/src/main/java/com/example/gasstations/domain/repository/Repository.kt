package com.example.gasstations.domain.repository

import com.example.gasstations.domain.models.RefuelModel

interface Repository {
    fun stations():List<RefuelModel>

    fun refuels(address:String):List<RefuelModel>
}