package com.example.gasstations.presentation.main_activity.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gasstations.data.storage.models.RefuelCache
import com.example.gasstations.databinding.ListItemBinding


class RefuelsListRecyclerAdapter(
    private var dataset: List<RefuelCache>,
    private val onRefuelLongClickListener: OnRefuelLongClickListener,
    private val onRefuelClickListener: OnRefuelClickListener

) :
    RecyclerView.Adapter<RefuelsListViewHolder>() {

    lateinit var binding: ListItemBinding

    fun update( refuels:List<RefuelCache>) {
        dataset = refuels
        notifyDataSetChanged() //TODO Diffutils
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
