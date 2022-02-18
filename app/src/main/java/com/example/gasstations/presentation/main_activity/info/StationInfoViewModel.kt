package com.example.gasstations.presentation.main_activity.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.gasstations.domain.models.InfoDomain
import com.example.gasstations.domain.usecase.GetStatisticsUseCase
import com.example.gasstations.presentation.BaseViewModel
import kotlinx.coroutines.launch

class StationInfoViewModel(private val getStatisticsUseCase: GetStatisticsUseCase):BaseViewModel() {
    var stationsInfoLiveData = MutableLiveData<List<InfoDomain>>()

    fun getInfo() {
        viewModelScope.launch {
            stationsInfoLiveData.postValue(getStatisticsUseCase.execute().sortedByDescending { it.totalVisits })
        }
    }
}