package com.yyogadev.growthyapplication.retrofit

import com.google.gson.annotations.SerializedName
import com.yyogadev.growthyapplication.retrofit.response.LoginResponse
import com.yyogadev.growthyapplication.retrofit.response.PlantResponse
import com.yyogadev.growthyapplication.retrofit.response.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @GET("/plant/plants")
    fun getPlant(
        @Query("q") query: String
    ): Call<PlantResponse>

//    @Multipart
//    @POST("/image")
//    fun uploadImage(
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//    ): Call<FileUploadResponse>

//    @GET("stories")
//    suspend fun getStories(
//        @Query("page") page: Int,
//        @Query("size") size: Int
//    ): Response<StoriesResponse>

//    @GET("stories/{id}")
//    fun getDetailStories(@Path("id") id: String): Call<DetailStoriesResponse>

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

//data class FileUploadResponse(
//    @field:SerializedName("message")
//    val message: String
//)

