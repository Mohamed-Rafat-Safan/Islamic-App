package com.example.myislamicapp.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myislamicapp.R
import com.example.myislamicapp.data.HomeContent
import com.example.myislamicapp.data.model.quran.Jozza
import com.example.myislamicapp.data.model.quran.Sora
import com.example.myislamicapp.databinding.FragmentHomeBinding
import com.example.myislamicapp.ui.quran.QuranViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener,
    HomeAdapter.ContentClickListener {
    private lateinit var mNavController: NavController
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        var listSoras = ArrayList<Sora>()
        var listJozza = ArrayList<Jozza>()
    }

    private lateinit var quranViewModel: QuranViewModel
    lateinit var adapter: HomeAdapter
    lateinit var contentList: ArrayList<HomeContent>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // indexAndJozzaViewModel
        quranViewModel = ViewModelProvider(this).get(QuranViewModel::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            listSoras = quranViewModel.getAllSoras()
            listJozza = quranViewModel.getAllJozza()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.drawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding.navigationView.itemIconTintList = null
        binding.navigationView.setNavigationItemSelectedListener(this)


        val actionToggle = ActionBarDrawerToggle(
            activity, binding.drawLayout, binding.customToolbarMainActivity,
            R.string.draw_open, R.string.draw_close
        )
        binding.drawLayout.addDrawerListener(actionToggle)
        actionToggle.syncState()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (isEnabled) {
                if (binding.drawLayout.isDrawerOpen(GravityCompat.START))
                    closeDrawer()
                else {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }


//        mNavController.currentBackStackEntry?.let { backEntry -> mNavController.popBackStack(backEntry.destination.id,true) }


        contentList = ArrayList()
        contentList.add(HomeContent("quran", R.drawable.quran_card, "القرأن الكريم"))
        contentList.add(HomeContent("azkar", R.drawable.zekr_hand, "الأذكار"))
        contentList.add(HomeContent("prayerTimes", R.drawable.prayer_time, "مواقيت الصلاة"))
        contentList.add(HomeContent("rosary", R.drawable.beads, "السبحة"))

        adapter = HomeAdapter(contentList, this)


        binding.rvHome.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHome.adapter = adapter






        return binding.root
    }


    // if click on any content in home
    override fun onItemClick(homeContent: HomeContent) {
        when (homeContent.en_name) {
            "quran" -> {
                val action = HomeFragmentDirections.actionHomeFragmentToQuranContainerFragment(-1)
                mNavController.navigate(action)
            }
            "azkar" -> {
                val action = HomeFragmentDirections.actionHomeFragmentToAzkarHomeFragment()
                mNavController.navigate(action)
            }
            "prayerTimes" -> {
                val action = HomeFragmentDirections.actionHomeFragmentToPrayersTimeFragment()
                mNavController.navigate(action)
            }
            "rosary" -> {
                val action = HomeFragmentDirections.actionHomeFragmentToRosaryFragment()
                mNavController.navigate(action)
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {

            }
            R.id.about -> {

            }
            R.id.exit -> {
                requireActivity().finish()
            }
        }
        closeDrawer() // this method to end or hide the Navigation drawer
        return true
    }

    fun closeDrawer() {
        binding.drawLayout.closeDrawer(GravityCompat.START)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}