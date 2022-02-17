package com.example.gasstations.data.repository

import com.example.gasstations.domain.models.RefuelModel
import com.example.gasstations.domain.repository.Repository

class RepositoryImpl : Repository {
    override fun stations(): List<RefuelModel> {
        return emptyList()
    }

    override fun refuels(address: String): List<RefuelModel> {
        TODO("Not yet implemented")
    }
}
