package com.example.myislamicapp.data.db

import androidx.room.*
import com.example.myislamicapp.data.model.prayers.DayPrayers
import com.example.myislamicapp.utils.Constant.Companion.PRAYERS_TIMINGS_TABLE

@Dao
interface PrayersTimingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayPrayers(dayPrayers: DayPrayers)

    @Update
    suspend fun updateDayPrayers(dayPrayers: DayPrayers)

    @Query("DELETE FROM $PRAYERS_TIMINGS_TABLE")
    fun clearDayPrayersTable()


    @Query("SELECT COUNT(*) FROM $PRAYERS_TIMINGS_TABLE")
    fun getRowCount(): Int


    @Query("SELECT * FROM $PRAYERS_TIMINGS_TABLE WHERE Day LIKE :day")
    suspend fun getDayPrayers(day: Int): DayPrayers
}