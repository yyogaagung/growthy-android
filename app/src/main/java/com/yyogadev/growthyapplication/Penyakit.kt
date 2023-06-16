package com.yyogadev.growthyapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Penyakit (
    val nama_tradisional: String,
    val nama_ilmiah: String,
    val photo: Int
) : Parcelable