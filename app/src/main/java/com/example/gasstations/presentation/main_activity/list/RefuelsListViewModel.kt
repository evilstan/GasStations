package com.example.gasstations.presentation.main_activity.list

import androidx.lifecycle.viewModelScope
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.usecase.DeleteByLocalUseCase
import com.example.gasstations.domain.usecase.GetAllRefuelsUseCase
import com.example.gasstations.domain.usecase.UpdateRefuelUseCase
import com.example.gasstations.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RefuelsListViewModel(
    getAllRefuelsUseCase: GetAllRefuelsUseCase,
    private val deleteByLocalUseCase: DeleteByLocalUseCase,
    private val updateRefuelUseCase: UpdateRefuelUseCase
) : BaseViewModel() {

    val refuelLiveData = getAllRefuelsUseCase.refuels

    fun updateStation(refuelCache: RefuelCache) {
        viewModelScope.launch(Dispatchers.IO) {
            updateRefuelUseCase.execute(refuelCache)
        }
    }

    fun deleteStation(refuelCache: RefuelCache) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteByLocalUseCase.execute(refuelCache)
        }
    }
}