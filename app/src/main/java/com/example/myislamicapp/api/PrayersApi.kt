package com.example.myislamicapp.api

import com.example.myislamicapp.data.model.prayers.PrayersApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PrayersApi {

//  calendarByCity/2017/4?city=London&country=United%20Kingdom&method=2


    @GET("calendarByCity")
    fun getPrayers(
        @Query("city")
        city: String = "Cairo",
        @Query("country")
        country: String = "Egypt",
        @Query("method")
        method: Int = 2,
        @Query("month")
        month: Int = 3,
        @Query("year")
        year: Int = 2020 ,
    ): Call<PrayersApiResponse>


//    // هذه الداله لكي تجيب كل الاخبار ,وليس الاخبار العاجله فقط
//    @GET("v2/everything")
//    suspend fun searchForNews(
//        @Query("q")
//        searchQuery: String,
//        @Query("page")
//        pageNumber: Int = 1,
//        @Query("apiKey")
//        apiKey: String = API_KEY
//    ): Response<NewsResponse>
}