package com.example.gasstations.presentation.main_activity.info

import com.example.gasstations.domain.usecase.GetInfoUseCase
import com.example.gasstations.presentation.BaseViewModel

class StationInfoViewModel(getInfoUseCase: GetInfoUseCase):BaseViewModel() {
    val stationsInfoLiveData = getInfoUseCase.info
}