package com.example.myislamicapp.data.model.quran

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jozza(
    val jozzaNumber: Int = 0,
    val startPage: Int = 0,
): Parcelable