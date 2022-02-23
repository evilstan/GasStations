package com.example.gasstations.data.repository

import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.repository.Repository

private const val DELTA = 0.001 //approx. +-120 meters

class RepositoryImpl(private val appDatabase: AppDatabase) : Repository {

    override suspend fun refuel(id: Long) =
        appDatabase.refuelDao().refuel(id)

    override fun allGasStations() =
        appDatabase.refuelDao().allGasStations()

    override fun allRefuels() =
        appDatabase.refuelDao().allRefuels()

    override fun newRefuels() =
        appDatabase.refuelDao().newRefuels()

    override fun deletedRefuels() =
        appDatabase.refuelDao().deletedRefuels()

    override suspend fun nearest(latitude: Double, longitude: Double) =
        appDatabase.refuelDao().nearest(latitude, longitude, DELTA)

    override suspend fun insert(refuelCache: RefuelCache) =
        appDatabase.refuelDao().insert(refuelCache)

    override suspend fun update(refuelCache: RefuelCache) =
        appDatabase.refuelDao().update(refuelCache)

    override suspend fun delete(id: Long) =
        appDatabase.refuelDao().delete(id)

    override suspend fun contains(id: Long) =
        appDatabase.refuelDao().contains(id)
}
