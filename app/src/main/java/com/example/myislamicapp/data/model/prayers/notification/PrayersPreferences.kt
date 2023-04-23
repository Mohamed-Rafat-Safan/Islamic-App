package com.example.myislamicapp.data.model.prayers.notification

import android.content.Context
import android.content.SharedPreferences


class PrayersPreferences(context: Context) {
    private val FILE_NAME = "PRAYERS_PREF"
    private val CITY_KEY_EN = "CITY_PREF_EN"
    private val CITY_KEY_AR = "CITY_PREF_AR"
    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    fun getCityNameEn(): String {
        return preferences.getString(CITY_KEY_EN, "Cairo").toString()
    }

    fun getCityNameAR(): String {
        return preferences.getString(CITY_KEY_AR, "القاهرة").toString()
    }

    fun setCityNameEn(city: String) {
        preferences.edit().putString(CITY_KEY_EN, city).apply()
    }

    fun setCityNameAR(city: String) {
        preferences.edit().putString(CITY_KEY_AR, city).apply()
    }

}