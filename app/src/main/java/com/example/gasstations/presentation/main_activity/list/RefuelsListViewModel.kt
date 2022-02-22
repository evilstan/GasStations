package com.example.gasstations.presentation.main_activity.list

import androidx.lifecycle.viewModelScope
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.domain.usecase.DeleteByLocalUseCase
import com.example.gasstations.domain.usecase.GetAllRefuelsUseCase
import com.example.gasstations.domain.usecase.GetNewItemsUseCase
import com.example.gasstations.presentation.BaseViewModel
import kotlinx.coroutines.launch

class RefuelsListViewModel(
    getAllRefuelsUseCase: GetAllRefuelsUseCase,
    private val deleteByLocalUseCase: DeleteByLocalUseCase,
    private val getNewItemsUseCase: GetNewItemsUseCase
) : BaseViewModel() {

    val refuelLiveData = getAllRefuelsUseCase.refuels

    fun deleteStation(refuelCache: RefuelCache) {
        viewModelScope.launch {
            deleteByLocalUseCase.execute(refuelCache)
        }
    }
}