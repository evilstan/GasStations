package com.example.gasstations.presentation.main_activity.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gasstations.databinding.StatisticsItemBinding
import com.example.gasstations.domain.models.StationInfoModel
import com.example.gasstations.presentation.main_activity.DiffUtilsCallback

class StationInfoRecyclerAdapter :
    RecyclerView.Adapter<StationInfoViewHolder>() {
    private lateinit var binding: StatisticsItemBinding
    private var dataset = emptyList<StationInfoModel>()

    fun update(stationInfoModels: List<StationInfoModel>) {
        val diffUtilsCallback = DiffUtilsCallback(dataset, stationInfoModels)
        val diffResult = DiffUtil.calculateDiff(diffUtilsCallback)
        dataset = stationInfoModels
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StationInfoViewHolder {
        binding = StatisticsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationInfoViewHolder, position: Int) {
        val infoDomain = dataset[position]
        holder.setItem(infoDomain)
    }

    override fun getItemCount() = dataset.size
}