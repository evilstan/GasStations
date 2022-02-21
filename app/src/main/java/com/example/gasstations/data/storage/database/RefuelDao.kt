package com.example.gasstations.data.storage.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gasstations.data.storage.models.RefuelModel

@Dao
interface RefuelDao {

    @Query("SELECT * FROM refuels WHERE deleted = 0")
    fun refuels(): LiveData<List<RefuelModel>>

//    @Query("SELECT * FROM refuels WHERE uploaded = 0 OR deleted = 1")
//    fun notUpdated(): LiveData<List<RefuelModel>>

    @Query("SELECT * FROM refuels WHERE deleted = 0")
    fun notUpdated(): LiveData<List<RefuelModel>>

    @Query(
        "SELECT * FROM refuels " +
                "WHERE (latitude BETWEEN (:latitude - :delta) AND (:latitude + :delta)) " +
                "AND (longitude BETWEEN (:longitude - :delta) AND (:longitude + :delta))"
    )
    suspend fun nearest(latitude: Double, longitude: Double, delta: Double): List<RefuelModel>

    @Query("DELETE FROM refuels WHERE id = :id ")
    suspend fun markDeleted(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(refuelModel: RefuelModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(refuelModel: RefuelModel)

    @Delete
    suspend fun delete(refuelModel: RefuelModel)

}