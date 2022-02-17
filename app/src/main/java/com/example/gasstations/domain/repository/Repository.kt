package com.example.gasstations.domain.repository

import com.example.gasstations.domain.models.RefuelDomain

interface Repository {
    fun stations():List<RefuelDomain>

    fun refuels(address:String):List<RefuelDomain>
}