package com.example.myislamicapp.data.repository


import com.example.myislamicapp.data.db.QuranDao
import com.example.myislamicapp.data.model.quran.Aya
import com.example.myislamicapp.data.model.quran.Jozza
import com.example.myislamicapp.data.model.quran.PageData
import com.example.myislamicapp.data.model.quran.Sora

class QuranRepository(private val quranDao: QuranDao) {


    fun getSoraName(pageNum: Int): PageData {
        return quranDao.getSoraNameByPage(pageNum)
    }

    suspend fun getAllSoras(): ArrayList<Sora> {
        val listSoraType = arrayListOf<Int>(
            2, 3, 4, 5, 8, 9, 13, 22, 24, 33, 47, 48,
            49, 55, 57, 58, 59, 60, 61, 62, 63, 64,
            65, 66, 76, 98, 99, 110
        )

        val listSoras = ArrayList<Sora>()

        for (i in 1..114) {
            val sora = quranDao.getSoraByNumber(i)

            if (listSoraType.contains(i)) {
                sora?.soraType = "city"
            }

            listSoras.add(sora!!)
        }

        return listSoras
    }


    suspend fun getAllJozza(): ArrayList<Jozza> {
        val listJozza = ArrayList<Jozza>()
        for (i in 1..30) {
            val jozza = quranDao.getJozzaByNumber(i)
            listJozza.add(jozza!!)
        }
        return listJozza
    }


    fun getAyaBySubText(subAya: String):List<Aya> {
        return quranDao.getAyaBySubText(subAya)
    }

}