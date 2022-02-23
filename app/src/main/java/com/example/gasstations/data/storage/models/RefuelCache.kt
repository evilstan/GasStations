package com.example.gasstations.data.storage.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

@Entity(tableName = "refuels")
data class RefuelCache(
    var brand: String,
    var latitude: Double,
    var longitude: Double,
    var fuelType: String,
    var fuelVolume: Double,
    var fuelPrice: Double,
    @PrimaryKey
    var id: Long,
    var uploaded: Boolean = false,
    var deleted: Boolean = false
) : ClusterItem {

    override fun getPosition() =
        LatLng(latitude, longitude)

    override fun getTitle() =
        brand

    override fun getSnippet() = ""
}