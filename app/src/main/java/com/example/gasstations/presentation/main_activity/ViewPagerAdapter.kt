package com.example.gasstations.presentation.main_activity

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gasstations.presentation.main_activity.info.StationInfoFragment
import com.example.gasstations.presentation.main_activity.list.RefuelsListFragment

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int) =
        if (position == 0) RefuelsListFragment()
        else StationInfoFragment()
}