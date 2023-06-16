package com.yyogadev.growthyapplication.retrofit.response

import com.google.gson.annotations.SerializedName

data class TanamanResponse(

	@field:SerializedName("data")
	val data: List<TanamanItem>,

	@field:SerializedName("message")
	val message: String
)

data class TanamanItem(

	@field:SerializedName("disease")
	val disease: List<DiseaseItem>,

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

	@field:SerializedName("createdAt")
	val createdAt: String,

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
	val prevention: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class DiseaseItem(

	@field:SerializedName("treatment")
	val treatment: String,

	@field:SerializedName("disease_img_zoom")
	val diseaseImgZoom: String,

	@field:SerializedName("disease_desc")
	val diseaseDesc: String,

	@field:SerializedName("disease_img_normal")
	val diseaseImgNormal: String,

	@field:SerializedName("scientic_name")
	val scienticName: String,

	@field:SerializedName("disease_local_name")
	val diseaseLocalName: String,

	@field:SerializedName("target")
	val target: String,

	@field:SerializedName("symptoms")
	val symptoms: String,

	@field:SerializedName("potention")
	val potention: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("causes")
	val causes: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("disease_img_wide")
	val diseaseImgWide: String,

	@field:SerializedName("prevention")
	val prevention: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
