package com.example.gasstations.presentation.main_activity.list

import androidx.recyclerview.widget.RecyclerView
import com.example.gasstations.R
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.databinding.ListItemBinding

class RefuelsListViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val view = binding.root

    fun setItem(refuelDomain: RefuelCache) {

        val name = getString(R.string.brand) + refuelDomain.brand

        val fuelType =
            getString(R.string.fuel_type) + refuelDomain.fuelType

        val coordinates =
            getString(R.string.station_address) +
                    refuelDomain.latitude + ", " +
                    refuelDomain.longitude

        val fuelVolume = getString(R.string.fuel_volume) +
                refuelDomain.fuelVolume.format(2) +
                getString(R.string.units)

        val fuelPrice = getString(R.string.fuel_price) +
                getString(R.string.currency) +
                refuelDomain.fuelPrice.format(2)

        binding.brand.text = name
        binding.stationAddress.text = coordinates
        binding.fuelType.text = fuelType
        binding.fuelVolume.text = fuelVolume
        binding.fuelPrice.text = fuelPrice
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun getString(resource: Int) = view.resources.getString(resource)


}