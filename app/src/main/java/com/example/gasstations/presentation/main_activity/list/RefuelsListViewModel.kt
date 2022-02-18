package com.example.gasstations.presentation.main_activity.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.domain.usecase.DeleteRefuelUseCase
import com.example.gasstations.domain.usecase.GetAllRefuelsUseCase
import com.example.gasstations.presentation.BaseViewModel
import kotlinx.coroutines.launch

class RefuelsListViewModel(
    private val getAllRefuelsUseCase: GetAllRefuelsUseCase,
    private val deleteRefuelUseCase: DeleteRefuelUseCase) : BaseViewModel() {
    var refuelLiveData = MutableLiveData<List<RefuelDomain>>()

    fun getStations() {
        viewModelScope.launch {
            refuelLiveData.postValue(getAllRefuelsUseCase.execute())
        }
    }

    fun deleteStation(refuelDomain: RefuelDomain){
        viewModelScope.launch {
            deleteRefuelUseCase.execute(refuelDomain)
            getStations()
        }
    }
}