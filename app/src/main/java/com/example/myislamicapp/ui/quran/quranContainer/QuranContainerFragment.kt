package com.example.myislamicapp.ui.quran.quranContainer


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myislamicapp.R
import com.example.myislamicapp.databinding.FragmentQuranContainerBinding
import com.example.myislamicapp.utils.Constant.Companion.saveLastPage
import com.example.myislamicapp.utils.Constant.Companion.saveTagPageNum
import com.example.myislamicapp.utils.Constant.Companion.sharedPrefSavePage


class QuranContainerFragment : Fragment() {
    private lateinit var mNavController: NavController

    companion object {
        private var _binding: FragmentQuranContainerBinding? = null
        val binding get() = _binding!!
    }


    private lateinit var pagerAdapter: QuranPagesAdapter
    private val args by navArgs<QuranContainerFragmentArgs>()

    private lateinit var savePageSharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuranContainerBinding.inflate(inflater, container, false)

        //  sharedPreferences
        savePageSharedPref = requireActivity().getSharedPreferences(sharedPrefSavePage, Context.MODE_PRIVATE)

//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            if (isEnabled) {
//                val action = QuranContainerFragmentDirections.actionQuranContainerFragmentSelf(null)
//                mNavController.navigate(action)
//
//                isEnabled = false
//                requireActivity().onBackPressed()
//            }
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.customToolbarQuran)
        (activity as AppCompatActivity?)!!.supportActionBar?.title = ""

        binding.pageNum.text = QuranPageFragment.pageData.pageNumber.toString()
        binding.soraName.text = QuranPageFragment.pageData.soraName
        binding.tvJozzaNum.text = QuranPageFragment.pageData.jozzaName


        pagerAdapter = QuranPagesAdapter(requireActivity())
        binding.quranPager.adapter = pagerAdapter

        // get last page user is exit
        val savedLastPage = savePageSharedPref.getInt(saveLastPage, -1)
        if(savedLastPage != -1){
            binding.quranPager.setCurrentItem(savedLastPage , false)
        }

//      binding.quranPager.currentItem = 10
        if (args.selectedPageStart != -1) {
            binding.quranPager.setCurrentItem(args.selectedPageStart - 1, false)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.quran_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                val action = QuranContainerFragmentDirections.actionQuranContainerFragmentToQuranSearchFragment()
                mNavController.navigate(action)
            }
            R.id.save_tag -> {

                val editor: SharedPreferences.Editor = savePageSharedPref.edit()
                editor.putInt(saveTagPageNum, binding.quranPager.currentItem)
                editor.putInt(saveLastPage, binding.quranPager.currentItem)
                editor.apply()

                val action = QuranContainerFragmentDirections.actionQuranContainerFragmentSelf(-1)
                mNavController.navigate(action)

            }
            R.id.move_to_tag -> {
                val savedPage = savePageSharedPref.getInt(saveTagPageNum, -1)

                val action = QuranContainerFragmentDirections.actionQuranContainerFragmentSelf(savedPage+1)
                mNavController.navigate(action)
            }
            R.id.quran_index -> {
                val action = QuranContainerFragmentDirections.actionQuranContainerFragmentToIndexAndJozzaFragment(0)
                mNavController.navigate(action)
            }
            R.id.quran_jozza -> {
                val action = QuranContainerFragmentDirections.actionQuranContainerFragmentToIndexAndJozzaFragment(1)
                mNavController.navigate(action)
            }
            R.id.seal_prayer -> {
                val action = QuranContainerFragmentDirections.actionQuranContainerFragmentToDoaaQuranEndFragment()
                mNavController.navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        val editor: SharedPreferences.Editor = savePageSharedPref.edit()
        editor.putInt(saveLastPage, binding.quranPager.currentItem)
        editor.apply()
    }



//  انا هنا عملته تعليق لانها بتخليلي ال  _binding بي null وده بيديني كراش
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

}