package com.example.gasstations.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gasstations.databinding.FragmentStationsListBinding

class StationsListFragment : Fragment() {

    private lateinit var binding: FragmentStationsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStationsListBinding.inflate(inflater, container, false)
        return binding.root
    }
}