package com.example.gasstations.presentation.main_activity.list

import androidx.lifecycle.viewModelScope
import com.example.gasstations.data.storage.models.RefuelModel
import com.example.gasstations.domain.usecase.DeleteRefuelUseCase
import com.example.gasstations.domain.usecase.GetAllRefuelsUseCase
import com.example.gasstations.presentation.BaseViewModel
import kotlinx.coroutines.launch

class RefuelsListViewModel(
    getAllRefuelsUseCase: GetAllRefuelsUseCase,
    private val deleteRefuelUseCase: DeleteRefuelUseCase) : BaseViewModel() {

    val refuelLiveData = getAllRefuelsUseCase.execute()

    fun deleteStation(refuelModel: RefuelModel){
        viewModelScope.launch {
            deleteRefuelUseCase.execute(refuelModel)
        }
    }
}