package com.example.myislamicapp.data.model.prayers

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myislamicapp.utils.Constant.Companion.PRAYERS_TIMINGS_TABLE

@Entity(tableName = PRAYERS_TIMINGS_TABLE)
data class DayPrayers(
    @PrimaryKey
    val Day: Int? = 1,
    val Month: Int,
    val Fajr: String,
    val Sunrise: String,
    val Dhuhr: String,
    val Asr: String,
    val Maghrib: String,
    val Isha: String
)
