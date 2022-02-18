package com.example.gasstations.presentation.main_activity.info

import androidx.recyclerview.widget.RecyclerView
import com.example.gasstations.R
import com.example.gasstations.databinding.StatisticsItemBinding
import com.example.gasstations.domain.models.InfoDomain

class StationInfoViewHolder(private val binding: StatisticsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val view = binding.root

    fun setItem(infoDomain: InfoDomain) {

        val name = getString(R.string.station_name) + infoDomain.name
        val address = getString(R.string.station_address) + infoDomain.address
        val visits = getString(R.string.station_visits) + infoDomain.totalVisits
        val totalFuel = getString(R.string.total_fuel) + infoDomain.totalFuel.format(2)
        binding.stationName.text = name
        binding.stationAddress.text = address
        binding.stationVisits.text = visits
        binding.stationTotalFuel.text = totalFuel

    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun getString(resource: Int) = view.resources.getString(resource)


}
