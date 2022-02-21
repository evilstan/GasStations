package com.example.gasstations.data.storage.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gasstations.data.storage.models.RefuelModel

@Dao
interface RefuelDao {

    @Query("SELECT * FROM refuels WHERE deleted = 0")
    fun refuels(): LiveData<List<RefuelModel>>

    @Query("SELECT * FROM refuels WHERE uploaded = 0")
    fun newRefuels(): LiveData<List<RefuelModel>>

    @Query("SELECT * FROM refuels WHERE deleted = 1")
    fun deletedRefuels(): LiveData<List<RefuelModel>>

    @Query(
        "SELECT * FROM refuels " +
                "WHERE (latitude BETWEEN (:latitude - :delta) AND (:latitude + :delta)) " +
                "AND (longitude BETWEEN (:longitude - :delta) AND (:longitude + :delta))" +
                "AND (deleted = 0)"
    )
    suspend fun nearest(latitude: Double, longitude: Double, delta: Double): List<RefuelModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(refuelModel: RefuelModel)

    @Update
    suspend fun update(refuelModel: RefuelModel)

    @Delete
    suspend fun delete(refuelModel: RefuelModel)

}