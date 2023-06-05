package com.yyogadev.growthyapplication.retrofit

import com.yyogadev.growthyapplication.retrofit.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/auth/login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/auth/register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignUpResponse>

    @GET("/financial/get_all_financial")
    fun getTransaksies(): Call<FinancialResponse>

    @GET("financial/get_financial/{id}")
    fun getOneFinancial(@Path("id") id: Int): Call<OneFinancialResponse>

    @FormUrlEncoded
    @PUT("financial/edit_financial/{id}")
    fun updatePengeluaranFinancial(
        @Path("id") id: Int,
        @Field("pengeluaran") nominal: Int,
        @Field("desc_pengeluaran") descPengeluaran: String,
        @Field("type") type : String

        ) : Call<EditFinancialResponse>

    @DELETE("/financial/delete_financial/{id}")
    fun deleteTransaksi(
        @Path("id") id: Int
    ): Call<EditFinancialResponse>

    @FormUrlEncoded
    @PUT("financial/edit_financial/{id}")
    fun updatePemasukanFinancial(
        @Path("id") id: Int,
        @Field("pemasukan") nominal: Int,
        @Field("desc_pemasukan") descPemasukan: String,
        @Field("type") type : String

    ) : Call<EditFinancialResponse>

    @POST("financial/add_financial")
    fun createPengeluaranFinancial(
        @Body request: CreatePengeluaranRequest
    ) : Call<EditFinancialResponse>


    @POST("financial/add_financial")
    fun createPemasukanFinancial(
        @Body request: CreatePemasukanRequest
    ) : Call<EditFinancialResponse>
//    @Multipart
//    @POST("stories")
//    fun addStory(
//        @Part("description") description: RequestBody,
//        @Part file: MultipartBody.Part
//    ): Call<PostStoryResponse>
//
//    @GET("stories?location=1")
//    fun getStoriesByLocation(): Call<StoriesResponse>
}