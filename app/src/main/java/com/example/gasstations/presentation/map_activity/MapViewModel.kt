package com.example.gasstations.presentation.map_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.domain.usecase.AddStationUseCase
import com.example.gasstations.domain.usecase.GetAllRefuelsUseCase
import com.example.gasstations.domain.usecase.IsNearestExistUseCase
import com.example.gasstations.presentation.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MapViewModel(
    private val addStationUseCase: AddStationUseCase,
    private val isNearestExistUseCase: IsNearestExistUseCase,
    private val getAllRefuelsUseCase: GetAllRefuelsUseCase
) : BaseViewModel() {
    val stationsLiveData = MutableLiveData<List<RefuelDomain>>()
    val checkLiveData = MutableLiveData<Boolean>()

    fun getAll() {
        viewModelScope.launch {
            stationsLiveData.postValue(getAllRefuelsUseCase.execute())
        }
    }

    fun check(stationPosition: LatLng) {
        viewModelScope.launch {
            checkLiveData.postValue(isNearestExistUseCase.execute(stationPosition))
        }
    }

    fun addStation(
        stationPosition: LatLng,
        fuelType: String,
        fuelAmount: Double,
        fuelPrice: Double
    ) {
        viewModelScope.launch {
            addStationUseCase.execute(
                stationPosition,
                fuelType,
                fuelAmount,
                fuelPrice
            )
        }
    }

    fun addStation(
        brand: String,
        stationPosition: LatLng,
        fuelType: String,
        fuelAmount: Double,
        fuelPrice: Double
    ) {
        viewModelScope.launch {
            addStationUseCase.execute(
                brand,
                stationPosition,
                fuelType,
                fuelAmount,
                fuelPrice
            )
        }
    }

}