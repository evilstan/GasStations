package com.example.gasstations.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gasstations.domain.models.RefuelDomain

interface Repository {
    fun stations(): MutableLiveData<List<RefuelDomain>>

    fun refuels(address:String):MutableLiveData<List<RefuelDomain>>
}