package com.example.gasstations.data.storage.database

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.gasstations.data.storage.models.RefuelCache
@Dao
interface RefuelDao {
    @Query("SELECT * FROM refuels")//TODO distinct???
   suspend fun refuels(): List<RefuelCache>

    @Insert //TODO onConflict  ????
    suspend fun insert(refuelCache: RefuelCache)

    @Query("DELETE FROM refuels WHERE id = :id ")
    suspend fun delete(id:Int)
}