package com.example.gasstations.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.databinding.ListItemBinding
import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.presentation.OnStationClickListener
import com.example.gasstations.presentation.RecyclerViewHolder

class RecyclerAdapter(
    private val dataset: List<RefuelDomain>,
    private val onClickListener: OnStationClickListener
) :
    RecyclerView.Adapter<RecyclerViewHolder>() {

    lateinit var binding: ListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val refuel = dataset[position]
        holder.setItem(refuel)
        holder.itemView.setOnClickListener { onClickListener.onClick(refuel) }
    }

    override fun getItemCount(): Int = dataset.size

}