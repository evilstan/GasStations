package com.example.gasstations.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gasstations.presentation.info.StationInfoFragment
import com.example.gasstations.presentation.list.StationsListFragment

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        lateinit var fragment: Fragment
        if (position == 0)
            fragment = StationsListFragment()
        else if (position == 1)
            fragment = StationInfoFragment()
        return fragment
    }

}