package com.example.gasstations.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gasstations.databinding.FragmentStationsListBinding
import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.presentation.OnStationClickListener

class StationsListFragment : Fragment(),
    OnStationClickListener {

    private lateinit var binding: FragmentStationsListBinding
    private lateinit var adapter: RecyclerAdapter
    private lateinit var viewModel: StationListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStationsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun initComponents() {
        viewModel = ViewModelProvider(this).get(StationListViewModel::class.java)
        viewModel.refuelLiveData.observe(viewLifecycleOwner) { adapter = RecyclerAdapter(it, this) }
        binding.gasStationsRecycler.adapter = adapter
    }

    override fun onClick(refuelDomain: RefuelDomain) {
        //TODO open info fragment + bundle
    }
}