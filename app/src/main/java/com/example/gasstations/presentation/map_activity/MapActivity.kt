package com.example.gasstations.presentation.map_activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gasstations.BuildConfig
import com.example.gasstations.R
import com.example.gasstations.data.core.App
import com.example.gasstations.data.repository.RepositoryImpl
import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.databinding.ActivityMapBinding
import com.example.gasstations.domain.usecase.AddRefuelUseCase
import com.example.gasstations.domain.usecase.GetAllRefuelsUseCase
import com.example.gasstations.domain.usecase.IsNearestExistUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient


class MapActivity :
    AppCompatActivity(),
    OnMapReadyCallback,
    View.OnClickListener, OnStationAddListener, OnRefuelAddListener {

    private lateinit var binding: ActivityMapBinding
    private lateinit var mapViewModel: MapViewModel
    private lateinit var map: GoogleMap
    private var cameraPosition: CameraPosition? = null

    private lateinit var placesClient: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var locationPermissionGranted = false

    private var lastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.title = resources.getString(R.string.map_activity_title)

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val repository = RepositoryImpl(
            AppDatabase.getInstance(
                App.instance
            )
        )
        mapViewModel = MapViewModel(
            AddRefuelUseCase(repository),
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

    override fun onSaveInstanceState(outState: Bundle) {
        map.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        googleMap.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM.toFloat())
        )


        updateLocationUI()
        getDeviceLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        println("Exception: %s" + task.exception)
                        map.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM.toFloat())
                        )
                        map.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            println("Exception: %s" + e.message)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
            updateLocationUI()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        try {
            if (locationPermissionGranted) {
                map.isMyLocationEnabled = true
                map.uiSettings.isMyLocationButtonEnabled = true
            } else {
                map.isMyLocationEnabled = false
                map.uiSettings.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            println("Exception: %s" + e.message)
        }
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

    companion object {
        private const val DEFAULT_ZOOM = 16
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private val DEFAULT_LOCATION = LatLng(50.395537692076516, 30.61595055046331)

        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}