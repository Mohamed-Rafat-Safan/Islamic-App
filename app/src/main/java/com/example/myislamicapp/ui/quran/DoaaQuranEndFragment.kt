package com.example.myislamicapp.ui.quran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myislamicapp.R
import com.example.myislamicapp.databinding.FragmentDoaaQuranEndBinding
import com.example.myislamicapp.databinding.FragmentIndexListBinding


class DoaaQuranEndFragment : Fragment() {
    private lateinit var mNavController: NavController
    private var _binding: FragmentDoaaQuranEndBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDoaaQuranEndBinding.inflate(inflater, container, false)



        binding.ivBackDoaa.setOnClickListener {
            mNavController.currentBackStackEntry?.let { backEntry -> mNavController.popBackStack(backEntry.destination.id,true) }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}