package com.example.gasstations.presentation.main_activity.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.databinding.ListItemBinding
import com.example.gasstations.presentation.main_activity.DiffUtilsCallback


class RefuelsListRecyclerAdapter(
    private var dataset: List<RefuelCache>,
    private val onRefuelLongClickListener: OnRefuelLongClickListener,
    private val onRefuelClickListener: OnRefuelClickListener

) :
    RecyclerView.Adapter<RefuelsListViewHolder>() {

    private lateinit var binding: ListItemBinding

    fun update(refuels:List<RefuelCache>) {
        val diffUtilsCallback = DiffUtilsCallback(dataset, refuels)
        val diffResult = DiffUtil.calculateDiff(diffUtilsCallback)
        dataset = refuels
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefuelsListViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RefuelsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RefuelsListViewHolder, position: Int) {
        val refuel = dataset[position]
        holder.setItem(refuel)
        holder.itemView.setOnLongClickListener { onRefuelLongClickListener.onLongClick(refuel) }
        holder.itemView.setOnClickListener{onRefuelClickListener.onClick(refuel)}
    }

    override fun getItemCount(): Int = dataset.size

}
