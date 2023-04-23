package com.example.myislamicapp.data.model.prayers

data class PrayersApiResponse(
    val code: Int,
    val `data`: List<Data>,
    val status: String
)