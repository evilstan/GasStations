package com.example.gasstations.presentation.main_activity.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gasstations.data.core.App
import com.example.gasstations.data.repository.RepositoryImpl
import com.example.gasstations.data.storage.database.AppDatabase
import com.example.gasstations.databinding.FragmentStationsListBinding
import com.example.gasstations.domain.models.RefuelDomain
import com.example.gasstations.domain.usecase.DeleteRefuelUseCase
import com.example.gasstations.domain.usecase.GetAllRefuelsUseCase
import com.example.gasstations.presentation.map_activity.MapActivity

class RefuelsListFragment : Fragment(),
    OnRefuelLongClickListener, View.OnClickListener, OnRefuelClickListener,
    OnRefuelChangedListener {

    private lateinit var binding: FragmentStationsListBinding
    private lateinit var adapter: RefuelsListRecyclerAdapter
    private lateinit var viewModel: RefuelsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStationsListBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStations()
    }

    private fun initComponents() {
        val repository = RepositoryImpl(AppDatabase.getInstance(App.instance))

        viewModel = RefuelsListViewModel(
            GetAllRefuelsUseCase(repository),
            DeleteRefuelUseCase(repository)
        )

        adapter = RefuelsListRecyclerAdapter(emptyList(), this,this)
        viewModel.refuelLiveData.observe(viewLifecycleOwner) { adapter.update(it) }

        binding.gasStationsRecycler.adapter = adapter
        binding.addFb.setOnClickListener(this)
    }

    override fun onLongClick(refuelDomain: RefuelDomain): Boolean {
        viewModel.deleteStation(refuelDomain)
        return true
    }

    override fun onClick(view: View?) {
        val intent = Intent(requireContext(), MapActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(refuelDomain: RefuelDomain): Boolean {
        ChangeRefuelDialog(this,refuelDomain).show(childFragmentManager,"")
        return true
    }

    override fun onChange(refuelDomain: RefuelDomain) {
        //TODO
    }
}