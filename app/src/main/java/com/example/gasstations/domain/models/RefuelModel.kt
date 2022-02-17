package com.example.gasstations.domain.models

data class RefuelModel(
    val gasStationName:String,
    val address:String,
    val fuelAmount:Double
){
    val id:Int = 0
    val synchronized = false
}