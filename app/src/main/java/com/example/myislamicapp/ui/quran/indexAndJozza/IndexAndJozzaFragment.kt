package com.example.myislamicapp.ui.quran.indexAndJozza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.myislamicapp.databinding.FragmentIndexAndJozzaBinding
import com.example.myislamicapp.ui.quran.quranContainer.QuranContainerFragmentArgs
import com.google.android.material.tabs.TabLayout


class IndexAndJozzaFragment : Fragment() {
    private lateinit var mNavController: NavController
    private var _binding: FragmentIndexAndJozzaBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: IndexAndJozzaAdapter

    private val args by navArgs<IndexAndJozzaFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIndexAndJozzaBinding.inflate(inflater, container, false)

        binding.apply {
            tabLayout.addTab(tabLayout.newTab().setText("الفهرس"))
            tabLayout.addTab(tabLayout.newTab().setText("الأجزاء"))
        }


        adapter = IndexAndJozzaAdapter(requireActivity())
        binding.vpContent.adapter = adapter

        // determined tap selected
        binding.vpContent.setCurrentItem(args.tapPosithion, false)
        binding.tabLayout.getTabAt(args.tapPosithion)?.select()


        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.vpContent.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })



        binding.apply {
            vpContent.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
        }



        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}