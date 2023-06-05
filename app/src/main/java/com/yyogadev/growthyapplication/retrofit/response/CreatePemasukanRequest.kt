package com.yyogadev.growthyapplication.retrofit.response

data class CreatePemasukanRequest(
    val pemasukan: Int,
    val desc_pemasukan: String,
    val type: String
)