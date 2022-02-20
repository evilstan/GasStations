package com.example.gasstations.presentation.map_activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.gasstations.R
import com.example.gasstations.data.core.App
import com.example.gasstations.data.repository.RepositoryImpl
import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.databinding.ActivityMapBinding
import com.example.gasstations.domain.usecase.AddStationUseCase
import com.example.gasstations.domain.usecase.GetAllRefuelsUseCase
import com.example.gasstations.domain.usecase.IsNearestExistUseCase
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapActivity :
    AppCompatActivity(),
    OnMapReadyCallback,
    View.OnClickListener, OnStationAddListener, OnRefuelAddListener {

    private lateinit var binding: ActivityMapBinding
    private lateinit var mapViewModel: MapViewModel
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = RepositoryImpl(
            AppDatabase.getInstance(
                App.instance
            )
        )
        mapViewModel = MapViewModel(
            AddStationUseCase(repository),
            IsNearestExistUseCase(repository),
            GetAllRefuelsUseCase(repository)
        )

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        binding.addStationButton.setOnClickListener(this)

        mapViewModel.stationsLiveData.observe(this) { it ->
            it.forEach {
                addMarker(LatLng(it.latitude, it.longitude), it.brand)
            }
        }

        mapViewModel.checkLiveData.observe(this) {
            add(it)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        googleMap.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(LatLng(50.401619825005845, 30.617504417896274), 18f)
        )
        mapViewModel.getAll()
    }

    override fun onClick(view: View?) {
        mapViewModel.check(map.cameraPosition.target)
    }

    private fun add(contains: Boolean) {
        if (contains) {
            AddRefuelDialog(this).show(supportFragmentManager, "")
        } else {
            AddGasStationDialog(this).show(supportFragmentManager, "")
        }
    }

    override fun addStation(
        brand: String,
        fuelType: String,
        fuelVolume: Double,
        fuelPrice: Double
    ) {
        mapViewModel.addStation(
            brand,
            map.cameraPosition.target,
            fuelType,
            fuelVolume,
            fuelPrice
        )
        addMarker(map.cameraPosition.target, brand)
    }

    override fun addRefuel(fuelType: String, fuelVolume: Double, fuelPrice: Double) {
        mapViewModel.addStation(
            map.cameraPosition.target,
            fuelType,
            fuelVolume,
            fuelPrice
        )
    }

    private fun addMarker(latLng: LatLng, title: String) {
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gas))
                .title(title)
        )
    }
}