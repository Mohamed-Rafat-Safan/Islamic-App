package com.example.myislamicapp.ui.quran.quranSearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myislamicapp.data.model.quran.Aya
import com.example.myislamicapp.databinding.FragmentQuranSearchBinding
import com.example.myislamicapp.ui.quran.QuranViewModel


class QuranSearchFragment : Fragment(), QuranSearchAdapter.SearchResultClickListener {
    private lateinit var mNavController: NavController
    private var _binding: FragmentQuranSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var quranViewModel: QuranViewModel
    private lateinit var adapter: QuranSearchAdapter
    private lateinit var listSearchResult: ArrayList<com.example.myislamicapp.data.model.quran.Aya>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
        listSearchResult = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentQuranSearchBinding.inflate(inflater, container, false)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listSearchResult = quranViewModel.getSearchResult(s.toString()) as ArrayList<Aya>
                adapter.updateListSearchResult(listSearchResult)
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        binding.ivBackQuranSearch.setOnClickListener {
            mNavController.currentBackStackEntry?.let { backEntry ->
                mNavController.popBackStack(
                    backEntry.destination.id,
                    true
                )
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel
        quranViewModel = ViewModelProvider(this).get(QuranViewModel::class.java)

        adapter = QuranSearchAdapter(requireContext(), this)
        binding.rvResultSearch.adapter = adapter
        binding.rvResultSearch.setHasFixedSize(true)


    }


    override fun onItemClick(aya: Aya) {
        val action = QuranSearchFragmentDirections.actionQuranSearchFragmentToQuranContainerFragment(aya.page)
        mNavController.navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}