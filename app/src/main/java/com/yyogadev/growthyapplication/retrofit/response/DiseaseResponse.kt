package com.yyogadev.growthyapplication

import com.google.gson.annotations.SerializedName

data class DiseaseResponse(

	@field:SerializedName("data")
	val data: List<DiseaseItem>,

	@field:SerializedName("message")
	val message: String
)

data class DiseaseItem(

	@field:SerializedName("treatment")
	val treatment: String? = null,

	@field:SerializedName("disease_img_zoom")
	val diseaseImgZoom: String? = null,

	@field:SerializedName("disease_desc")
	val diseaseDesc: String? = null,

	@field:SerializedName("disease_img_normal")
	val diseaseImgNormal: String? = null,

	@field:SerializedName("scientic_name")
	val scienticName: String? = null,

	@field:SerializedName("disease_local_name")
	val diseaseLocalName: String? = null,

	@field:SerializedName("target")
	val target: String? = null,

	@field:SerializedName("symptoms")
	val symptoms: String? = null,

	@field:SerializedName("potention")
	val potention: String? = null,

	@field:SerializedName("causes")
	val causes: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("disease_img_wide")
	val diseaseImgWide: String? = null,

	@field:SerializedName("prevention")
	val prevention: String? = null
)
