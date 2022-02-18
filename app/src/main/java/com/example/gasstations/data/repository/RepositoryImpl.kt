package com.example.gasstations.data.repository

import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.data.storage.firebase.FirebaseDb
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.repository.Repository

class RepositoryImpl(private val appDatabase: AppDatabase) : Repository {
    override suspend fun refuels(): List<RefuelCache> {
        return appDatabase.refuelDao().refuels()
    }

    override suspend fun insert(refuelCache: RefuelCache) {
        appDatabase.refuelDao().insert(refuelCache)
    }

    override suspend fun delete(id: Int) {
        appDatabase.refuelDao().delete(id)
    }
}
