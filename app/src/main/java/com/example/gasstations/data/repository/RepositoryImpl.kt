package com.example.gasstations.data.repository

import androidx.lifecycle.LiveData
import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.data.storage.models.RefuelModel
import com.example.gasstations.domain.repository.Repository

private const val DELTA = 0.001 //approx. +-120 meters

class RepositoryImpl(private val appDatabase: AppDatabase) : Repository {
    override fun refuels(): LiveData<List<RefuelModel>> {
        return appDatabase.refuelDao().refuels()
    }

    override fun notUpdated() =
        appDatabase.refuelDao().notUpdated()

    override suspend fun nearest(latitude: Double, longitude: Double) =
        appDatabase.refuelDao().nearest(latitude, longitude, DELTA)

    override suspend fun insert(refuelModel: RefuelModel) =
        appDatabase.refuelDao().insert(refuelModel)

    override suspend fun update(refuelModel: RefuelModel) =
        appDatabase.refuelDao().update(refuelModel)

    override suspend fun markDeleted(id: Int) =
        appDatabase.refuelDao().markDeleted(id)

    override suspend fun delete(refuelModel: RefuelModel) =
        appDatabase.refuelDao().delete(refuelModel)
}
