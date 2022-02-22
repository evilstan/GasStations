package com.example.gasstations.presentation.map_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gasstations.domain.usecase.AddRefuelUseCase
import com.example.gasstations.domain.usecase.GetAllRefuelsUseCase
import com.example.gasstations.domain.usecase.IsNearestExistUseCase
import com.example.gasstations.presentation.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(
    private val addRefuelUseCase: AddRefuelUseCase,
    private val isNearestExistUseCase: IsNearestExistUseCase,
    getAllRefuelsUseCase: GetAllRefuelsUseCase
) : BaseViewModel() {
    var stationsLiveData = getAllRefuelsUseCase.refuels
    val checkLiveData = MutableLiveData<Boolean>()

    fun check(stationPosition: LatLng) {
        viewModelScope.launch(Dispatchers.IO)  {
            checkLiveData.postValue(isNearestExistUseCase.execute(stationPosition))
        }
    }

    fun addStation(
        stationPosition: LatLng,
        fuelType: String,
        fuelVolume: Double,
        fuelPrice: Double
    ) {
        viewModelScope.launch(Dispatchers.IO)  {
            addRefuelUseCase.execute(
                stationPosition,
                fuelType,
                fuelVolume,
                fuelPrice
            )
        }
    }

    fun addStation(
        brand: String,
        stationPosition: LatLng,
        fuelType: String,
        fuelVolume: Double,
        fuelPrice: Double
    ) {
        viewModelScope.launch(Dispatchers.IO)  {
            addRefuelUseCase.execute(
                brand,
                stationPosition,
                fuelType,
                fuelVolume,
                fuelPrice
            )
        }
    }

}