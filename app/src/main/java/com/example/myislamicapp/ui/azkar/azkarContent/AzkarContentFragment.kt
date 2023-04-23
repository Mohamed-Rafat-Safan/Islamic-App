package com.example.myislamicapp.ui.azkar.azkarContent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myislamicapp.data.model.azkar.Zekr
import com.example.myislamicapp.databinding.FragmentAzkarContentBinding
import com.example.myislamicapp.databinding.ItemZekrContentListBinding
import com.example.myislamicapp.ui.azkar.AzkarViewModel


class AzkarContentFragment : Fragment() {
    private lateinit var mNavController: NavController
    private var _binding: FragmentAzkarContentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AzkarContentAdapter
    private lateinit var azkarViewModel: AzkarViewModel

    private var progr = 0

    private val args by navArgs<AzkarContentFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAzkarContentBinding.inflate(inflater, container, false)

        val zekrType = arguments?.getString("zekrType")
        binding.tvZekrType.text = zekrType

        azkarViewModel = ViewModelProvider(this).get(AzkarViewModel::class.java)
        val zekrList = azkarViewModel.getAzkarByType(zekrType!!)
        for (i in 0 until zekrList.size) {
            zekrList[i].zekrNum = i + 1

            if(zekrList[i].count == ""){
                zekrList[i].count = "1"
            }
        }

        adapter = AzkarContentAdapter(requireContext())
        adapter.updateListZekrContent(zekrList)

        binding.apply {
            rvZekrContent.adapter = adapter
            rvZekrContent.setHasFixedSize(true)
        }

        binding.ivBackAzkar.setOnClickListener {
            mNavController.currentBackStackEntry?.let { backEntry -> mNavController.popBackStack(backEntry.destination.id,true) }
        }

        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}