package com.example.gasstations.data.repository

import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.domain.repository.Repository

class RepositoryImpl : Repository {
    override fun stations(): List<RefuelDomain> {
        return emptyList()
    }

    override fun refuels(address: String): List<RefuelDomain> {
        TODO("Not yet implemented")
    }
}
