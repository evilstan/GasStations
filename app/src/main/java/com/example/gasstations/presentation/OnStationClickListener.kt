package com.example.gasstations.presentation

import com.example.gasstations.domain.models.RefuelDomain

interface OnStationClickListener {
    fun onClick(refuelDomain:RefuelDomain)
}