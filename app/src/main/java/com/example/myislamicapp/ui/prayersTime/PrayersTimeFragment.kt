package com.example.myislamicapp.ui.prayersTime

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myislamicapp.R
import com.example.myislamicapp.data.model.prayers.City
import com.example.myislamicapp.data.model.prayers.notification.PrayersPreferences
import com.example.myislamicapp.data.model.prayers.PrayersTiming
import com.example.myislamicapp.databinding.FragmentPrayersTimeBinding
import com.example.myislamicapp.databinding.ItemCitiesDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PrayersTimeFragment : Fragment(), PrayersTimeAdapter.PrayerClickListener,
    CitiesAdapter.CityClickListener {
    private lateinit var mNavController: NavController
    private var _binding: FragmentPrayersTimeBinding? = null
    private val binding get() = _binding!!

    private lateinit var prayerAdapter: PrayersTimeAdapter
    private lateinit var cityAdapter: CitiesAdapter
    private lateinit var prayerViewModel: PrayersViewModel
    private lateinit var preferences: PrayersPreferences

    // dialog of cities
    private lateinit var dialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
        prayerAdapter = PrayersTimeAdapter(requireContext(), this)
        cityAdapter = CitiesAdapter(requireContext(), this)
        preferences = PrayersPreferences(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPrayersTimeBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.customToolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // when click on back in toolbar
        binding.customToolbar.setNavigationOnClickListener {
            mNavController.currentBackStackEntry?.let { backEntry -> mNavController.popBackStack(backEntry.destination.id,true) }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // PrayersViewModel
        prayerViewModel = ViewModelProvider(this).get(PrayersViewModel::class.java)


        // get cities from database of cities and put Bottom Sheet Dialog
        val listCities = prayerViewModel.getAllCities()
        cityAdapter.updateListCities(listCities as ArrayList<City>)
        binding.tvSelectCity.setOnClickListener {
            showBottomSheet()
        }


        binding.tvSelectCity.text = preferences.getCityNameAR()
        // this function if you change in DatePicker
        listenerDatePicker()

        binding.tvToDay.setOnClickListener {
            listenerDatePicker()
            binding.tvToDay.visibility = View.INVISIBLE
        }



        binding.apply {
            rvPrayersTimes.adapter = prayerAdapter
            rvPrayersTimes.setHasFixedSize(true)
        }

        if (hasInternetConnection()) {
            prayerViewModel.prayersTimings.observe(viewLifecycleOwner) { listPrayers ->
                // call adapter and put data in it
                prayerAdapter.updateListPrayers(listPrayers)
            }
        }


    }


    private fun listenerDatePicker() {
        val calendar = Calendar.getInstance()
        val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
        val monthName = monthFormat.format(calendar.time)

        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val dayName = dayFormat.format(calendar.time)

        updateDateOfDatePicker(
            dayName,
            calendar.get(Calendar.DAY_OF_MONTH).toString(),
            monthName,
            calendar.get(Calendar.YEAR).toString()
        )

        if (hasInternetConnection()) {
            prayerViewModel.getPrayersTimings(
                preferences.getCityNameEn(), "Egypt", 3,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                false
            )
        } else {
            // get prayers from database
            setPrayersAdapterFromDB(
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1
            )
        }


        binding.dpCalender.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            binding.tvToDay.visibility = View.VISIBLE

            val newCalendar = Calendar.getInstance()
            newCalendar.set(year, monthOfYear, dayOfMonth)
            val dayName =
                newCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            val monthName =
                newCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

            updateDateOfDatePicker(dayName!!, dayOfMonth.toString(), monthName!!, year.toString())

            if (hasInternetConnection()) {
                prayerViewModel.getPrayersTimings(
                    "Cairo",
                    "Egypt",
                    3,
                    dayOfMonth,
                    monthOfYear + 1,
                    monthOfYear,
                    true
                )
            } else {
                // get prayers from database
                setPrayersAdapterFromDB(dayOfMonth, monthOfYear + 1)
            }
        }
    }


    private fun updateDateOfDatePicker(
        dayName: String,
        dayNum: String,
        monthName: String,
        year: String
    ) {
        binding.apply {
            tvDayName.text = dayName
            tvDay.text = dayNum
            tvMonth.text = monthName
            tvYear.text = year
        }
    }


    private fun showBottomSheet() {
        val layoutBinding = ItemCitiesDialogBinding.inflate(layoutInflater, null, false)

        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(layoutBinding.root)

        layoutBinding.rvCities.layoutManager = LinearLayoutManager(requireContext())
        layoutBinding.rvCities.adapter = cityAdapter
        layoutBinding.rvCities.setHasFixedSize(true)

        // when search for city filter the recyclerView
        layoutBinding.etSearchCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val listSearchResult =
                    prayerViewModel.getCitySearched(s.toString()) as ArrayList<City>
                cityAdapter.updateListCities(listSearchResult)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        dialog.show()
    }


    private fun setPrayersAdapterFromDB(day: Int, month: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var listPrayers = ArrayList<PrayersTiming>()
            var prayers = prayerViewModel.getDayPrayers(day)
            if (prayerViewModel.getRowCount() == 0) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "لا يوجد اتصال بالانترنت", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }
            if (prayers != null && prayers.Month != month) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, "لا يوجد هذا الشهر", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }
            if (prayers != null) {
                listPrayers.add(PrayersTiming("الفجر", prayers.Fajr.split(" ")[0], prayers.Fajr.split(" ")[1]))
                listPrayers.add(PrayersTiming("الشروق", prayers.Sunrise.split(" ")[0], prayers.Sunrise.split(" ")[1]))
                listPrayers.add(PrayersTiming("الظهر", prayers.Dhuhr.split(" ")[0], prayers.Dhuhr.split(" ")[1]))
                listPrayers.add(PrayersTiming("العصر", prayers.Asr.split(" ")[0], prayers.Asr.split(" ")[1]))
                listPrayers.add(PrayersTiming("المغرب", prayers.Maghrib.split(" ")[0], prayers.Maghrib.split(" ")[1]))
                listPrayers.add(PrayersTiming("العشاء", prayers.Isha.split(" ")[0], prayers.Isha.split(" ")[1]))

                // this update list prayers in recyclerView
                withContext(Dispatchers.Main) {
                    prayerAdapter.updateListPrayers(listPrayers)
                }

            }

        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = requireContext().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }

        return false
    }


    // if click on any prayer
    override fun onItemClick(parTiming: PrayersTiming) {

    }


    // if click on any city in dialog
    override fun onItemClick(city: City) {
        if (hasInternetConnection()) {
            binding.tvSelectCity.text = city.city_name_ar

            // add new city arabic to shared preferences
            preferences.setCityNameAR(city.city_name_ar)

            val calendar = Calendar.getInstance()
            prayerViewModel.getPrayersTimings(
                city.city_name_en, "Egypt", 3,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                false
            )
        } else {
            Toast.makeText(activity, "لا يوجد اتصال بالانترنت", Toast.LENGTH_SHORT).show()
        }

        dialog.dismiss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}