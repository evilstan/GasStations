package com.example.gasstations.data.storage.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gasstations.data.storage.models.RefuelCache

@Dao
interface RefuelDao {

    @Query("SELECT EXISTS (SELECT 1 FROM refuels WHERE id = :id)")
    suspend fun contains(id: Long): Boolean

    @Query("SELECT * FROM refuels WHERE deleted = 0")
    fun allRefuels(): LiveData<List<RefuelCache>>

    @Query("SELECT * FROM refuels WHERE uploaded = 0")
    fun newRefuels(): LiveData<List<RefuelCache>>

    @Query("SELECT * FROM refuels WHERE deleted = 1")
    fun deletedRefuels(): LiveData<List<RefuelCache>>

    @Query(
        "SELECT * FROM refuels " +
                "WHERE (latitude BETWEEN (:latitude - :delta) AND (:latitude + :delta)) " +
                "AND (longitude BETWEEN (:longitude - :delta) AND (:longitude + :delta))" +
                "AND (deleted = 0)"
    )
    suspend fun nearest(latitude: Double, longitude: Double, delta: Double): List<RefuelCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(refuelCache: RefuelCache)

    @Update
    suspend fun update(refuelCache: RefuelCache)

    @Query("DELETE FROM refuels WHERE id = :id")
    suspend fun delete(id: Long)

}