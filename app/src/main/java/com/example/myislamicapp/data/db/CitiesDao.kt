package com.example.myislamicapp.data.db

import androidx.room.Dao
import androidx.room.Query
import com.example.myislamicapp.data.model.prayers.City
import com.example.myislamicapp.data.model.quran.Aya
import com.example.myislamicapp.utils.Constant.Companion.CITIES_TABLE

@Dao
interface CitiesDao {

    @Query("SELECT * FROM $CITIES_TABLE")
    fun getAllCities(): List<City>

    @Query("SELECT * FROM ${CITIES_TABLE} WHERE city_name_ar LIKE '%' || :subCity || '%'")
    fun getCityBySubText(subCity: String): List<City>

}
