package com.yyogadev.growthyapplication.retrofit.response

import com.google.gson.annotations.SerializedName

data class OneFinancialResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("pemasukan")
	val pemasukan: Any,

	@field:SerializedName("date_time")
	val dateTime: Any,

	@field:SerializedName("pengeluaran")
	val pengeluaran: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("desc_pemasukan")
	val descPemasukan: String,

	@field:SerializedName("desc_pengeluaran")
	val descPengeluaran: Any,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
