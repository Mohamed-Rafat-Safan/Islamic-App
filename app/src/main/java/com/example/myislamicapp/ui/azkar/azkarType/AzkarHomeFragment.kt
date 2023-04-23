package com.example.myislamicapp.ui.azkar.azkarType

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myislamicapp.data.model.azkar.ZekrType
import com.example.myislamicapp.databinding.FragmentAzkarHomeBinding
import com.example.myislamicapp.ui.azkar.AzkarViewModel
import com.example.myislamicapp.ui.quran.QuranViewModel


class AzkarHomeFragment : Fragment(), AzkarTypeAdapter.ZekrClickListener {
    private lateinit var mNavController: NavController
    private var _binding: FragmentAzkarHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var azkarViewModel: AzkarViewModel
    private lateinit var adapter: AzkarTypeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAzkarHomeBinding.inflate(inflater, container, false)

        azkarViewModel = ViewModelProvider(this).get(AzkarViewModel::class.java)

        adapter = AzkarTypeAdapter(requireContext(), this)
        adapter.updateListZekrType(azkarViewModel.getAzkarTypes())

        binding.apply {
            rvAzkar.adapter = adapter
            rvAzkar.setHasFixedSize(true)
        }

        binding.ivBackAzkar.setOnClickListener {
            mNavController.currentBackStackEntry?.let { backEntry -> mNavController.popBackStack(backEntry.destination.id,true) }
        }

        return binding.root
    }

    override fun onItemClick(zekrType: com.example.myislamicapp.data.model.azkar.ZekrType) {
        val action = AzkarHomeFragmentDirections.actionAzkarHomeFragmentToAzkarContentFragment(zekrType.zekrName)
        mNavController.navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}