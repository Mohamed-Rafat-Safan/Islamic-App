package com.example.myislamicapp.ui.quran.quranContainer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myislamicapp.utils.Constant

class QuranPagesAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = Constant.PAGES_NUM

    override fun createFragment(position: Int): Fragment = QuranPageFragment.newInstance(position+1)

}
