package com.example.gasstations.presentation

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.databinding.ListItemBinding
import com.example.gasstations.domain.models.RefuelDomain

class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ListItemBinding.inflate(LayoutInflater.from(view.context))

    fun setItem(refuelCache: RefuelDomain) {
        binding.stationName.text = refuelCache.gasStationName
        binding.stationAddress.text = refuelCache.address
    }
}