package com.example.volunteerku.service

import android.location.Location
import com.example.volunteerku.data.CommonResponse
import com.example.volunteerku.data.DuplicateResponse
import com.example.volunteerku.data.EmailResponse
import com.example.volunteerku.data.LogoutResponse
import com.example.volunteerku.data.RefreshTokenResponse
import com.example.volunteerku.data.User
import com.example.volunteerku.data.EmailCertifyCodeResponse
import retrofit2.Call
import retrofit2.http.*

interface UserRetrofitInterface {

    @POST("/api/users/register")
    fun register(@Body user: User): Call<EmailCertifyCodeResponse>

    @GET("/api/users/duplication")
    fun isDuplicate(@Query("nickname") nickname: String): Call<DuplicateResponse>

    @POST("/api/auth/emails")
    fun sendEmail(@Body target: String): Call<EmailResponse>

    @GET("/api/auth/emails")
    fun certifyCode(
        @Query("email") email: String,
        @Query("code") code: String
    ): Call<EmailCertifyCodeResponse>

}