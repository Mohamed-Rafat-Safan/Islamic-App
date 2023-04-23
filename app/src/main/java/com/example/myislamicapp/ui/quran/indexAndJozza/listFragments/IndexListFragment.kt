package com.example.myislamicapp.ui.quran.indexAndJozza.jozzaList.soraList


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myislamicapp.databinding.FragmentIndexListBinding
import com.example.myislamicapp.ui.home.HomeFragment
import com.example.myislamicapp.ui.quran.QuranViewModel
import com.example.myislamicapp.ui.quran.indexAndJozza.IndexAndJozzaFragmentDirections


class IndexListFragment : Fragment(), IndexAdapter.SoraClickListener {
    private lateinit var mNavController: NavController
    private var _binding: FragmentIndexListBinding? = null
    private val binding get() = _binding!!

    private lateinit var quranViewModel: QuranViewModel
    private lateinit var adapter: IndexAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIndexListBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel
        quranViewModel = ViewModelProvider(this).get(QuranViewModel::class.java)

        adapter = IndexAdapter(requireContext(), this)
        adapter.updateListSora(HomeFragment.listSoras)

        binding.apply {
            rvSora.adapter = adapter
            rvSora.setHasFixedSize(true)
        }

    }


    override fun onItemClick(sora: com.example.myislamicapp.data.model.quran.Sora) {
        val action = IndexAndJozzaFragmentDirections.actionIndexAndJozzaFragmentToQuranContainerFragment(sora.startPage)
        mNavController.navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}