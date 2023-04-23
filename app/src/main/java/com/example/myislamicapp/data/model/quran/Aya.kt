package com.example.myislamicapp.data.model.quran

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myislamicapp.utils.Constant.Companion.QURAN_TABLE

@Entity(tableName = QURAN_TABLE)
data class Aya(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val jozz: Int,
    val sora: Int,
    val sora_name_en: String,
    val sora_name_ar: String,
    val page: Int,
    val line_start: Int,
    val line_end: Int,
    val aya_no: Int,
    val aya_text: String,
    val aya_text_emlaey: String
)

