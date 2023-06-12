package com.yyogadev.growthyapplication.retrofit.response

import com.google.gson.annotations.SerializedName

data class DetailProfileResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("financial_dashboard")
	val financialDashboard: List<Any?>? = null,

	@field:SerializedName("address")
	val address: Any? = null,

	@field:SerializedName("gender")
	val gender: Any? = null,

	@field:SerializedName("balance")
	val balance: Any? = null,

	@field:SerializedName("phone")
	val phone: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("avatar")
	val avatar: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
