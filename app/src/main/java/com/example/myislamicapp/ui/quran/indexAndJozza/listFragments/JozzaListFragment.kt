package com.example.myislamicapp.ui.quran.indexAndJozza.listFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myislamicapp.databinding.FragmentJozzaListBinding
import com.example.myislamicapp.ui.home.HomeFragment
import com.example.myislamicapp.ui.quran.QuranViewModel
import com.example.myislamicapp.ui.quran.indexAndJozza.IndexAndJozzaFragmentDirections


class JozzaListFragment : Fragment(), JozzaAdapter.JozzaClickListener {
    private lateinit var mNavController: NavController
    private var _binding: FragmentJozzaListBinding? = null
    private val binding get() = _binding!!

    private lateinit var quranViewModel: QuranViewModel
    private lateinit var adapter: JozzaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentJozzaListBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SoraListViewModel
        quranViewModel = ViewModelProvider(this).get(QuranViewModel::class.java)

        adapter = JozzaAdapter(requireContext(), this)
        adapter.updateListJozza(HomeFragment.listJozza)

        binding.apply {
            rvJozza.adapter = adapter
            rvJozza.setHasFixedSize(true)
        }

    }


    override fun onItemClick(jozza: com.example.myislamicapp.data.model.quran.Jozza) {
        val action = IndexAndJozzaFragmentDirections.actionIndexAndJozzaFragmentToQuranContainerFragment(jozza.startPage)
        mNavController.navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}