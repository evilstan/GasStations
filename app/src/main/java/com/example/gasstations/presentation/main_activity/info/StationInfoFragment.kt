package com.example.gasstations.presentation.main_activity.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gasstations.data.core.App
import com.example.gasstations.data.repository.RepositoryImpl
import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.databinding.FragmentStationInfoBinding
import com.example.gasstations.domain.usecase.GetStatisticsUseCase

class StationInfoFragment : Fragment() {
    private lateinit var binding: FragmentStationInfoBinding
    private lateinit var viewModel: StationInfoViewModel
    private lateinit var adapter: StationInfoRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStationInfoBinding.inflate(inflater)
        initComponents()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getInfo()
    }

    private fun initComponents() {
        viewModel = StationInfoViewModel(
            GetStatisticsUseCase(
                RepositoryImpl(
                    AppDatabase.getInstance(
                        App.instance
                    )
                )
            )
        )

        adapter = StationInfoRecyclerAdapter()
        binding.gasStationsRecycler.adapter = adapter
        viewModel.stationsInfoLiveData.observe(viewLifecycleOwner) { adapter.update(it) }
    }

}