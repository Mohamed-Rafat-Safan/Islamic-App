package com.example.myislamicapp.data.repository

import com.example.myislamicapp.data.db.CitiesDao
import com.example.myislamicapp.data.db.PrayersTimingsDao
import com.example.myislamicapp.data.model.prayers.DayPrayers
import com.mohamedrafat.newsapp.api.RetrofitInstance

class PrayersRepository(
    private val citiesDao: CitiesDao,
    private val prayersTimingsDao: PrayersTimingsDao
) {

    fun getPrayersTimes(
        city: String,
        country: String,
        method: Int,
        month: Int,
        year: Int,
    ) = RetrofitInstance.api.getPrayers(city, country, method, month, year)


    // get all cities from database
    fun getAllCities() = citiesDao.getAllCities()

    fun getCitySearched(subTextCity: String) = citiesDao.getCityBySubText(subTextCity)


    suspend fun insertDayPrayers(dayPrayers: DayPrayers) {
        prayersTimingsDao.insertDayPrayers(dayPrayers)
    }

    suspend fun getOneDayPrayers(day: Int): DayPrayers {
        return prayersTimingsDao.getDayPrayers(day)
    }


    fun getRowCount() = prayersTimingsDao.getRowCount()

    fun clearDayPrayersTable() {
        prayersTimingsDao.clearDayPrayersTable()
    }

}

