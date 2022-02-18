package com.example.gasstations.presentation.main_activity.list

import androidx.recyclerview.widget.RecyclerView
import com.example.gasstations.R
import com.example.gasstations.databinding.ListItemBinding
import com.example.gasstations.domain.models.RefuelDomain

class RefuelsListViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val view = binding.root

    fun setItem(refuelCache: RefuelDomain) {

        val name = getString(R.string.station_name) + refuelCache.name
        val address = getString(R.string.station_address) + refuelCache.address
        val fuelAmount = getString(R.string.fuel_amount) + refuelCache.fuelAmount.format(2)

        binding.stationName.text = name
        binding.stationAddress.text = address
        binding.fuelAmount.text = fuelAmount
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun getString(resource: Int) = view.resources.getString(resource)


}