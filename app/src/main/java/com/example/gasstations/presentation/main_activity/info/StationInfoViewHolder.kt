package com.example.gasstations.presentation.main_activity.info

import androidx.recyclerview.widget.RecyclerView
import com.example.gasstations.R
import com.example.gasstations.databinding.StatisticsItemBinding
import com.example.gasstations.domain.models.StationInfoModel

class StationInfoViewHolder(private val binding: StatisticsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val view = binding.root

    fun setItem(stationInfoModel: StationInfoModel) {

        val name = getString(R.string.brand) + stationInfoModel.name
        val address = getString(R.string.station_address) + stationInfoModel.address
        val visits = getString(R.string.station_visits) + stationInfoModel.totalVisits
        val totalFuel = getString(R.string.total_fuel) +
                stationInfoModel.totalFuel.format(2) +
                getString(R.string.units)
        binding.brand.text = name
        binding.stationAddress.text = address
        binding.stationVisits.text = visits
        binding.stationTotalFuel.text = totalFuel

    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun getString(resource: Int) = view.resources.getString(resource)


}
