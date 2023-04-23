package com.example.myislamicapp.data.repository

import android.content.Context
import com.example.myislamicapp.data.model.azkar.Zekr
import com.example.myislamicapp.data.model.azkar.ZekrType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors


class AzkarRepository(val context: Context) {

    fun getAllAzkar(): ArrayList<Zekr> {
        val azkarFile: InputStream = context.assets.open("azkar/azkar.json")

        val size: Int = azkarFile.available()
        val bytes = ByteArray(size)

        azkarFile.read(bytes)
        azkarFile.close()

        val azkarString = String(bytes, StandardCharsets.UTF_8)
        val gson = Gson()

        return gson.fromJson(azkarString, object : TypeToken<List<Zekr>>() {}.type)
    }


    // this return set to is not repeat
    fun getAzkarTypes(): MutableSet<ZekrType> {
        return getAllAzkar()
            .stream()
            .map { zekr -> ZekrType(zekr.category) }
            .collect(Collectors.toCollection {
                mutableSetOf()
            })
    }


    fun getAzkarByType(zekrType: String): ArrayList<Zekr> {
        return getAllAzkar()
            .stream()
            .filter { zekr -> zekrType == zekr.category }
            .collect(Collectors.toCollection { ArrayList() })
    }

}