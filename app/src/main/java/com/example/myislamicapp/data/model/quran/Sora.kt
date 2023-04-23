package com.example.myislamicapp.data.model.quran

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sora(
    val soraNumber: Int = 0,
    var soraType: String? = "maka",
    val startPage: Int = 0,
    val endPage: Int = 0,
    val ayatNum: Int = 0,
    val arabicName: String = "",
    val englishName: String = ""
):Parcelable
