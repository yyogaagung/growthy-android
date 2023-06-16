package com.yyogadev.growthyapplication.retrofit.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(

    @field:SerializedName("message")
    val message: String
)