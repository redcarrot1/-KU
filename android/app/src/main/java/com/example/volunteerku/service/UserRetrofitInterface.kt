package com.example.volunteerku.service

import com.example.volunteerku.data.DuplicateResponse
import com.example.volunteerku.data.EmailCertifyCodeResponse
import com.example.volunteerku.data.EmailResponse
import com.example.volunteerku.data.SaveImageResponse
import com.example.volunteerku.data.SignupRequest
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface UserRetrofitInterface {

    @POST("/api/users/register")
    fun signup(@Body request: SignupRequest): Call<Void>

    @GET("/api/users/duplication")
    fun isDuplicate(@Query("nickname") nickname: String): Call<DuplicateResponse>

    @POST("/api/auth/emails")
    fun sendEmail(@Body target: String): Call<EmailResponse>

    @GET("/api/auth/emails")
    fun certifyCode(
        @Query("email") email: String,
        @Query("code") code: String
    ): Call<EmailCertifyCodeResponse>

    @Multipart
    @POST("/api/users/images")
    fun saveImage(@Part faceImageFile: MultipartBody.Part): Call<SaveImageResponse>

}