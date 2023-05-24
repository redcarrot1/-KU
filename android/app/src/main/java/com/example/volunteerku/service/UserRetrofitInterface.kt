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

    @POST("/api/users/login")
    fun login(@Header("Authorization") token: String): Call<EmailCertifyCodeResponse>

    @POST("/api/users/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>

    @POST("/api/auth/tokens")
    fun reissuanceToken(@Header("Authorization") token: String): Call<RefreshTokenResponse>

    @POST("/api/auth/emails")
    fun sendEmail(@Body target: String): Call<EmailResponse>

    @GET("/api/auth/emails")
    fun certifyCode(
        @Query("email") email: String,
        @Query("code") code: String
    ): Call<EmailCertifyCodeResponse>

    @POST("/api/attendances")
    fun attendanceToday(
        @Header("Authorization") token: String,
        @Body location: Location
    ): Call<CommonResponse>

    @GET("/api/attendances")
    fun getAttendanceDates(@Header("Authorization") token: String): Call<CommonResponse>

    @GET("/api/landmarks")
    fun getNearLandmark(
        @Header("Authorization") token: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Call<CommonResponse>

    @GET("/api/scores/rankings")
    fun getUserRanking(@Header("Authorization") token: String): Call<CommonResponse>

    @GET("/api/scores/rankings/top100")
    fun getTop100Ranking(@Header("Authorization") token: String): Call<CommonResponse>

    @POST("/api/scores")
    fun updateUserScore(
        @Header("Authorization") token: String,
        @Body scoreType: String
    ): Call<CommonResponse>

    @GET("/api/adventures")
    fun getUserAdventureLog(@Header("Authorization") token: String): Call<CommonResponse>

    @GET("/api/adventures/{landmarkId}/most")
    fun getPlaceRank(
        @Header("Authorization") token: String,
        @Path("landmarkId") landmarkId: Int
    ): Call<CommonResponse>

    @POST("/api/badges")
    fun saveBadge(
        @Header("Authorization") token: String,
        @Body badgeType: String
    ): Call<CommonResponse>

    @GET("/api/badges")
    fun getBadgeList(@Header("Authorization") token: String): Call<CommonResponse>

    @DELETE("/api/users")
    fun deleteUser(@Header("Authorization") token: String): Call<CommonResponse>

    @GET("/api/users")
    fun getUserInfo(@Header("Authorization") token: String): Call<CommonResponse>

}