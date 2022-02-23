package com.example.gasstations.presentation.map_activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.gasstations.R
import com.example.gasstations.data.storage.models.RefuelCache
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class PlaceRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<RefuelCache>
) : DefaultClusterRenderer<RefuelCache>(context, map, clusterManager) {

    private val bicycleIcon: BitmapDescriptor by lazy {
        BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource( context.resources, R.drawable.gas))
    }

    override fun onBeforeClusterItemRendered(
        item: RefuelCache,
        markerOptions: MarkerOptions
    ) {
        markerOptions.title(item.title)
            .position(item.position)
            .icon(bicycleIcon)
    }

    override fun onClusterItemRendered(clusterItem: RefuelCache, marker: Marker) {
        marker.tag = clusterItem
    }

}