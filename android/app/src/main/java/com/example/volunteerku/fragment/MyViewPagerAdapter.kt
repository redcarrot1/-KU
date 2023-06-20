package com.example.volunteerku.fragment

import VolunteerSearch
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> com.example.volunteerku.fragment.ListFragment()
            1 -> VolunteerSearch()
            2 -> MypageActivity()
            else -> MypasswordChangeActivity()
        }
    }

}