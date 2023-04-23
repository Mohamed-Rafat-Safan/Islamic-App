package com.example.myislamicapp.ui.quran.quranContainer


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myislamicapp.data.model.quran.PageData
import com.example.myislamicapp.databinding.FragmentQuranPageBinding
import com.example.myislamicapp.ui.quran.QuranViewModel
import com.example.myislamicapp.utils.Constant.Companion.saveTagPageNum
import com.example.myislamicapp.utils.Constant.Companion.sharedPrefSavePage


class QuranPageFragment : Fragment() {
    private lateinit var mNavController: NavController
    private var _binding: FragmentQuranPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var quranViewModel: QuranViewModel

    private lateinit var savePageSharedPref: SharedPreferences

    companion object {
        var pageData = com.example.myislamicapp.data.model.quran.PageData()

        var POSITION_ARG = "postion_arg"

        @JvmStatic
        fun newInstance(postion: Int) =
            QuranPageFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION_ARG, postion)
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuranPageBinding.inflate(inflater, container, false)

        mNavController = findNavController()

        // quranViewModel
        quranViewModel = ViewModelProvider(this).get(QuranViewModel::class.java)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pos = arguments?.getInt(POSITION_ARG)
        pageData = quranViewModel.getSoraName(pos!!)

        QuranContainerFragment.binding.pageNum.text = pageData.pageNumber.toString()
        QuranContainerFragment.binding.soraName.text = pageData.soraName
        QuranContainerFragment.binding.tvJozzaNum.text = pageData.jozzaName


        //  get data from Shared Preference
        savePageSharedPref = requireActivity().getSharedPreferences(
            sharedPrefSavePage,
            Context.MODE_PRIVATE
        )
        val savedPage = savePageSharedPref.getInt(saveTagPageNum, -1)
//        Toast.makeText(activity, ""+savedPage , Toast.LENGTH_SHORT).show()
        if (savedPage + 1 == pos) {
            binding.ivSaveOnPage.visibility = View.VISIBLE
        } else {
            binding.ivSaveOnPage.visibility = View.GONE
        }


        val quranPage = quranViewModel.getQuranImageByPageNumber(requireContext(), pos)
        binding.ivQuranPage.setImageDrawable(quranPage)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}