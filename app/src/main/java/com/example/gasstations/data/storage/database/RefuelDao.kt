package com.example.gasstations.data.storage.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gasstations.data.storage.models.RefuelModel

@Dao
interface RefuelDao {

    @Query("SELECT * FROM refuels")
    fun refuels(): LiveData<List<RefuelModel>>

    @Query(
        "SELECT * FROM refuels " +
                "WHERE (latitude BETWEEN (:latitude - :delta) AND (:latitude + :delta)) " +
                "AND (longitude BETWEEN (:longitude - :delta) AND (:longitude + :delta))"
    )
    suspend fun nearest(latitude: Double, longitude: Double, delta: Double): List<RefuelModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(refuelModel: RefuelModel)

    @Query("DELETE FROM refuels WHERE id = :id ")
    suspend fun delete(id: Int)
}