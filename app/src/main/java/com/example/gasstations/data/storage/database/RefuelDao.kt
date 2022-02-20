package com.example.gasstations.data.storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gasstations.data.storage.models.RefuelCache

@Dao
interface RefuelDao {

    @Query("SELECT * FROM refuels")
    suspend fun refuels(): List<RefuelCache>

    @Query(
        "SELECT * FROM refuels " +
                "WHERE (latitude BETWEEN (:latitude - :delta) AND (:latitude + :delta)) " +
                "AND (longitude BETWEEN (:longitude - :delta) AND (:longitude + :delta))"
    )
    suspend fun nearest(latitude: Double, longitude: Double, delta: Double): List<RefuelCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(refuelCache: RefuelCache)

    @Query("DELETE FROM refuels WHERE id = :id ")
    suspend fun delete(id: Int)
}