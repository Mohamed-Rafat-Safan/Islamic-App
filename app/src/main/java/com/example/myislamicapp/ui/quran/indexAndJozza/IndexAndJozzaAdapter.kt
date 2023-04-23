package com.example.myislamicapp.ui.quran.indexAndJozza

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myislamicapp.ui.quran.indexAndJozza.listFragments.JozzaListFragment
import com.example.myislamicapp.ui.quran.indexAndJozza.jozzaList.soraList.IndexListFragment

class IndexAndJozzaAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            IndexListFragment()
        else
            JozzaListFragment()
    }

}
