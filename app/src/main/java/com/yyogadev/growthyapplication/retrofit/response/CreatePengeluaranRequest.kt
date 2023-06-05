package com.yyogadev.growthyapplication.retrofit.response

data class CreatePengeluaranRequest(
    val pengeluaran: Int,
    val desc_pengeluaran: String,
    val type: String
)
