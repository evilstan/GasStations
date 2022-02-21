package com.example.gasstations.data.repository

import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.data.storage.models.RefuelModel
import com.example.gasstations.domain.repository.Repository

private const val DELTA = 0.001 //approx. +-120 meters

class RepositoryImpl(private val appDatabase: AppDatabase) : Repository {
    override fun refuels() = appDatabase.refuelDao().refuels()

    override suspend fun nearest(latitude: Double, longitude: Double) =
        appDatabase.refuelDao().nearest(latitude, longitude, DELTA)

    override suspend fun insert(refuelModel: RefuelModel) {
        appDatabase.refuelDao().insert(refuelModel)
    }

    override suspend fun delete(id: Int) {
        appDatabase.refuelDao().delete(id)
    }
}
