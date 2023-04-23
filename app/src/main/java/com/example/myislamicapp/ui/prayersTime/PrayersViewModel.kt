package com.example.myislamicapp.ui.prayersTime


import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.myislamicapp.data.db.CitiesDatabase
import com.example.myislamicapp.data.db.PrayersTimingsDatabase
import com.example.myislamicapp.data.model.prayers.*
import com.example.myislamicapp.data.model.prayers.notification.AzanPrayersUtil
import com.example.myislamicapp.data.model.prayers.notification.PrayersPreferences
import com.example.myislamicapp.data.repository.PrayersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class PrayersViewModel(val app: Application) : AndroidViewModel(app) {
    private val preferences = PrayersPreferences(app)
    private val prayersRepository: PrayersRepository

    init {
        val citiesDao = CitiesDatabase.getDatabase(app).citiesDao()
        val prayersTimingsDao = PrayersTimingsDatabase.getDatabase(app).prayersTimingsDao()
        prayersRepository = PrayersRepository(citiesDao, prayersTimingsDao)
    }


    val prayersTimings: MutableLiveData<ArrayList<PrayersTiming>> = MutableLiveData()


    fun getPrayersTimings(
        city: String = "",
        country: String = "Egypt",
        method: Int,
        day: Int = 1,
        month: Int = 4,
        year: Int = 2021,
        isCalender: Boolean
    ) {
        prayersRepository.getPrayersTimes(city, country, method, month, year)
            .enqueue(object : retrofit2.Callback<PrayersApiResponse> {
                override fun onResponse(
                    call: Call<PrayersApiResponse>,
                    response: Response<PrayersApiResponse>
                ) {
                    val timings = response.body()?.data?.get(day - 1)?.timings
                    val prayers = convertFromTimings(timings)
                    prayersTimings.value = prayers


                    if (!isCalender) {
                        // insert month prayers in database
                        CoroutineScope(Dispatchers.IO).launch {
                            val calendar = Calendar.getInstance()
                            val currentMonth = calendar.get(Calendar.MONTH) + 1
                            if (prayersRepository.getRowCount() == 0) {
                                insertPrayersInDB(response.body()?.data!!, month)
                            } else if (prayersRepository.getOneDayPrayers(day).Month != currentMonth) {
                                prayersRepository.clearDayPrayersTable()
                                insertPrayersInDB(response.body()?.data!!, month)
                            } else if (preferences.getCityNameEn() != city) {
                                prayersRepository.clearDayPrayersTable()
                                insertPrayersInDB(response.body()?.data!!, month)
                            }

                            // register prayers time if i change the city
                            if (preferences.getCityNameEn() != city) {
                                AzanPrayersUtil().registerPrayers(app)

                                // add new city english to shared preferences
                                preferences.setCityNameEn(city)
                            }
                        }  // End CoroutineScope
                    }

                }

                override fun onFailure(call: Call<PrayersApiResponse>, t: Throwable) {
                    Log.e("prayersViewModel", "" + t.message)
                }
            })

    }


    fun convertFromTimings(timings: Timings?): ArrayList<PrayersTiming> {
        val res: ArrayList<PrayersTiming> = ArrayList()
        if (timings != null) {
            res.add(PrayersTiming("الفجر", timings.Fajr.split(" ")[0], "AM"))
            res.add(PrayersTiming("الشروق", timings.Sunrise.split(" ")[0], "AM"))
            if(timings.Dhuhr.split(" ")[0] == "11"){
                res.add(PrayersTiming("الظهر", timings.Dhuhr.split(" ")[0], "AM"))
            }else{
                res.add(PrayersTiming("الظهر", timings.Dhuhr.split(" ")[0], "PM"))
            }
            res.add(PrayersTiming("العصر", timings.Asr.split(" ")[0], "PM"))
            res.add(PrayersTiming("المغرب", timings.Maghrib.split(" ")[0], "PM"))
            res.add(PrayersTiming("العشاء", timings.Isha.split(" ")[0], "PM"))
        }
        return res
    }


    // get all cities from database of cities
    fun getAllCities(): List<City> {
        return prayersRepository.getAllCities()
    }


    fun getCitySearched(subTextCity: String): List<City> {
        return prayersRepository.getCitySearched(subTextCity)
    }


    fun insertPrayersInDB(monthPrayers: List<Data>, month: Int) {
        var dayPrayersObj: DayPrayers
        var fajr: String
        var sunrise: String
        var dhuhr: String
        var asr: String
        var maghrib: String
        var isha: String

        CoroutineScope(Dispatchers.IO).launch {
            for (day in monthPrayers.indices) {
                fajr = "${monthPrayers[day].timings.Fajr.split(" ")[0]} AM"
                sunrise = "${monthPrayers[day].timings.Sunrise.split(" ")[0]} AM"
                dhuhr = "${monthPrayers[day].timings.Dhuhr.split(" ")[0]} "

                if (dhuhr.substring(0, 2) == "11") dhuhr += "AM"
                else dhuhr += "PM"

                asr = "${monthPrayers[day].timings.Asr.split(" ")[0]} PM"
                maghrib = "${monthPrayers[day].timings.Maghrib.split(" ")[0]} PM"
                isha = "${monthPrayers[day].timings.Isha.split(" ")[0]} PM"

                dayPrayersObj = DayPrayers(day + 1, month, fajr, sunrise, dhuhr, asr, maghrib, isha)

                prayersRepository.insertDayPrayers(dayPrayersObj)
            }
        }
    }


    suspend fun getDayPrayers(day: Int): DayPrayers {
        return prayersRepository.getOneDayPrayers(day)
    }


    fun getRowCount() = prayersRepository.getRowCount()
}