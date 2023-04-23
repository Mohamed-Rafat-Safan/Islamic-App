package com.example.myislamicapp.data.model.prayers

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myislamicapp.utils.Constant.Companion.CITIES_TABLE

@Entity(tableName = CITIES_TABLE)
data class City(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val city_name_ar: String,
    val city_name_en: String
)
