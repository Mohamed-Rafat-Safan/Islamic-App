package com.example.myislamicapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myislamicapp.data.model.prayers.DayPrayers
import com.example.myislamicapp.utils.Constant.Companion.DATABASE_PRAYERS_TIMINGS

@Database(entities = [DayPrayers::class], version = 1, exportSchema = false)
abstract class PrayersTimingsDatabase : RoomDatabase() {

    abstract fun prayersTimingsDao(): PrayersTimingsDao

    companion object {
        @Volatile
        private var INSTANCE: PrayersTimingsDatabase? = null

        fun getDatabase(context: Context): PrayersTimingsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrayersTimingsDatabase::class.java,
                    DATABASE_PRAYERS_TIMINGS
                ).build()

                INSTANCE = instance
                return instance
            }
        }

    }
}