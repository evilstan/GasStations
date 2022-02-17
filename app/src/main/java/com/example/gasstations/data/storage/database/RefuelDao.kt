package com.example.gasstations.data.storage.database

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.gasstations.data.storage.models.RefuelCache
@Dao
interface RefuelDao {
    @Query("SELECT * FROM refuels")
    suspend fun refuels(): MutableLiveData<List<RefuelCache>>

    @Query("SELECT * FROM refuels WHERE id = :id")
    suspend fun statistics(id: Int): MutableLiveData<List<RefuelCache>>
}