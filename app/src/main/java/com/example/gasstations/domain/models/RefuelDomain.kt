package com.example.gasstations.domain.models

data class RefuelDomain(
    val name:String,
    val address:String,
    val fuelAmount:Double,
    val id:Int = 0
)