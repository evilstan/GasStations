package com.example.gasstations.presentation.main_activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.gasstations.R
import com.example.gasstations.databinding.ActivityMainBinding
import com.example.gasstations.presentation.RefuelsSyncService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        tabLayout = binding.tabLayout
        viewPager = binding.pager

        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val text = when (position) {
                0 -> resources.getString(R.string.refuels_list)
                1 -> resources.getString(R.string.station_statistics)
                else -> ""
            }
            tab.text = text
        }.attach()

        val intent = Intent(this, RefuelsSyncService::class.java)
        startService(intent)
    }
}