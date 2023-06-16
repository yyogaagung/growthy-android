package com.yyogadev.growthyapplication.retrofit.response

import com.google.gson.annotations.SerializedName

data class OneTanamanResponse(

	@field:SerializedName("data")
	val data: OneTanamanData,

	@field:SerializedName("message")
	val message: String
)

data class OneTanamanData(

	@field:SerializedName("plant_disease")
	val plantDisease: String,

	@field:SerializedName("plant_img_zoom")
	val plantImgZoom: String,

	@field:SerializedName("plant_img_wide")
	val plantImgWide: String,

	@field:SerializedName("local_name")
	val localName: String,

	@field:SerializedName("cultivation")
	val cultivation: String,

	@field:SerializedName("difficulty")
	val difficulty: String,

	@field:SerializedName("plant_img_normal")
	val plantImgNormal: String,

	@field:SerializedName("species")
	val species: String,

	@field:SerializedName("genus")
	val genus: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("ordo")
	val ordo: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("plant_desc")
	val plantDesc: String,

	@field:SerializedName("prevention")
	val prevention: String
)
