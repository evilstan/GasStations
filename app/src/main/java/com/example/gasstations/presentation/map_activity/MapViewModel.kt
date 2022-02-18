package com.example.gasstations.presentation.map_activity

import androidx.lifecycle.viewModelScope
import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.domain.usecase.AddStationUseCase
import com.example.gasstations.presentation.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

class MapViewModel(private val addStationUseCase: AddStationUseCase) : BaseViewModel() {

    fun addRandom() {
        viewModelScope.launch {
            for(i in 1..10){
                addStationUseCase.execute(

                    RefuelDomain(
                        "Okko",
                        "Bazhana " + Random.nextInt(1, 10),
                        Random.nextDouble(10.0, 100.0),
                    )

                )
            }

        }
    }

}