package com.example.myislamicapp.ui.quran

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import com.example.myislamicapp.data.db.QuranDatabase
import com.example.myislamicapp.data.repository.QuranRepository

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val quranRepository: QuranRepository

    init {
        val quranDao = QuranDatabase.getDatabase(application).quranDao()
        quranRepository = QuranRepository(quranDao)
    }


    fun getQuranImageByPageNumber(context: Context, pageNumber: Int): Drawable? {
        return com.example.myislamicapp.data.model.quran.PagesManger.getQuranImageByPageNumber(context, pageNumber)
    }

    fun getSoraName(pageNum: Int): com.example.myislamicapp.data.model.quran.PageData {
        return quranRepository.getSoraName(pageNum)
    }

    suspend fun getAllSoras(): ArrayList<com.example.myislamicapp.data.model.quran.Sora> {
        return quranRepository.getAllSoras()
    }

    suspend fun getAllJozza(): ArrayList<com.example.myislamicapp.data.model.quran.Jozza> {
        return quranRepository.getAllJozza()
    }

    fun getSearchResult(key: String): List<com.example.myislamicapp.data.model.quran.Aya> {
        return quranRepository.getAyaBySubText(key)
    }

}