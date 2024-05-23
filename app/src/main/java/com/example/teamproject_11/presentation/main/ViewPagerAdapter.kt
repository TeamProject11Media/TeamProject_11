package com.example.teamproject_11.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.teamproject_11.presentation.home.main.HomeFragment
import com.example.teamproject_11.presentation.myvideo.MyVideoFragment
import com.example.teamproject_11.presentation.search.SearchFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments = mutableListOf(
        HomeFragment(),
        SearchFragment(),
        MyVideoFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}