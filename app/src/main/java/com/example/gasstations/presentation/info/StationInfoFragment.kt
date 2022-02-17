package com.example.gasstations.presentation.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gasstations.databinding.FragmentStationInfoBinding

class StationInfoFragment:Fragment() {
    lateinit var  binding : FragmentStationInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStationInfoBinding.inflate(inflater)
        return binding.root
    }

}