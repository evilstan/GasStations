package com.example.gasstations.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.domain.usecase.GetAllStationsUseCase
import com.example.gasstations.presentation.BaseViewModel
import kotlinx.coroutines.launch

class StationListViewModel(private val getAllStationsUseCase: GetAllStationsUseCase) : BaseViewModel() {
    var refuelLiveData = MutableLiveData<List<RefuelDomain>>()

    fun getStations() {
        viewModelScope.launch {
            refuelLiveData = getAllStationsUseCase.execute()
        }
    }
}