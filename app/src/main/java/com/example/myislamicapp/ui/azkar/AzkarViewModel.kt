package com.example.myislamicapp.ui.azkar

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.myislamicapp.data.model.azkar.Zekr
import com.example.myislamicapp.data.model.azkar.ZekrType
import com.example.myislamicapp.data.repository.AzkarRepository


class AzkarViewModel(application: Application) : AndroidViewModel(application) {

    private val azkarRepository = AzkarRepository(application)


    fun getAzkarTypes(): MutableSet<com.example.myislamicapp.data.model.azkar.ZekrType> {
        return azkarRepository.getAzkarTypes()
    }


    fun getAzkarByType(type: String): ArrayList<com.example.myislamicapp.data.model.azkar.Zekr> {
        return azkarRepository.getAzkarByType(type)
    }
}