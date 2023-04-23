package com.example.myislamicapp.data.model

data class Rosary(
    val rosaryName: String,
    val rosaryNum: Int = 0,
    var lastCount: Int = 0,
)