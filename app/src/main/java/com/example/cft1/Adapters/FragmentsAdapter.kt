package com.example.cft1.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cft1.Fragments.HistoryOfLookUp
import com.example.cft1.Fragments.LookUpBin


class FragmentsAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    private var numFragment = true

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment

        if (numFragment == true) {
            numFragment = false
            fragment = LookUpBin()
        }
        else{
            numFragment = true
            fragment = HistoryOfLookUp()
        }

        return fragment
    }

}