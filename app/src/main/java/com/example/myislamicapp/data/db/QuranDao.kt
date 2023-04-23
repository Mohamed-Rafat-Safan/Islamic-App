package com.example.myislamicapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.myislamicapp.data.model.quran.Aya
import com.example.myislamicapp.data.model.quran.Jozza
import com.example.myislamicapp.data.model.quran.PageData
import com.example.myislamicapp.data.model.quran.Sora
import com.example.myislamicapp.utils.Constant.Companion.QURAN_TABLE


@Dao
interface QuranDao {

    @Query("SELECT * FROM $QURAN_TABLE WHERE page = :page")
    fun getAyaByPage(page: Int): LiveData<List<Aya>>

    @Query("SELECT page as pageNumber, sora_name_ar as soraName, jozz as jozzaName FROM $QURAN_TABLE WHERE page = :pageNum")
    fun getSoraNameByPage(pageNum: Int): PageData

    @Query(
        "SELECT sora as soraNumber, MIN(page) as startPage ,MAX(page) as endPage ," +
                "MAX(aya_no) as ayatNum, sora_name_ar as arabicName,sora_name_en as englishName " +
                "FROM $QURAN_TABLE WHERE sora = :soraNumber"
    )
    suspend fun getSoraByNumber(soraNumber: Int): Sora?


    @Query("SELECT jozz as jozzaNumber, MIN(page) as startPage FROM $QURAN_TABLE WHERE jozz = :jozzaNumber")
    suspend fun getJozzaByNumber(jozzaNumber: Int): Jozza?


    @Query("SELECT * FROM $QURAN_TABLE WHERE aya_text_emlaey LIKE '%' || :subAya || '%'")
    fun getAyaBySubText(subAya: String): List<Aya>

}