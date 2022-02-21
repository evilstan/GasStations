package com.example.gasstations.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.example.gasstations.data.storage.models.RefuelModel
import com.example.gasstations.domain.asFlow
import com.example.gasstations.domain.models.StationInfoModel
import com.example.gasstations.domain.repository.Repository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class GetInfoUseCase(private val repository: Repository) {

    init {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                repository.refuels().asFlow().collect { value ->
                    info.postValue(calculate(value))
                }
            }
        }
    }

    val info = MutableLiveData<List<StationInfoModel>>()

    private fun calculate(refuels: List<RefuelModel>) =
        refuels.groupBy { LatLng(it.latitude, it.longitude) }.map {
            map(it.value)
        }

    private fun map(refuels: List<RefuelModel>) =
        StationInfoModel(
            refuels.first().brand,
            "" + refuels.first().latitude + ", " + refuels.first().longitude,
            refuels.size,
            refuels.sumOf { it.fuelVolume }
        )

}