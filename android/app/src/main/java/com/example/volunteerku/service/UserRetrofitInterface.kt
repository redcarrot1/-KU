package com.example.volunteerku.service


import android.location.Location
import com.example.volunteerku.data.ChangePasswordRequest
import com.example.volunteerku.data.CommonResponse
import com.example.volunteerku.data.DuplicateResponse
import com.example.volunteerku.data.EmailCertifyCodeResponse
import com.example.volunteerku.data.ExistEmailResponse
import com.example.volunteerku.data.JWT
import com.example.volunteerku.data.EmailResponse
import com.example.volunteerku.data.Room
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

    @POST("/api/users/login")
    fun signIn(
        @Body request: ChangePasswordRequest
    ): Call<JWT>

    @POST("/api/users/password")
    fun changePassword(
        @Body request: ChangePasswordRequest
    ):Call<Void>

    @GET("/api/users/exist")
    fun isMember(
        @Query("email") email:String
    ):Call<ExistEmailResponse>

    @POST("/api/rooms/register")
    fun createPost(@Header("Authorization") accessToken: String, @Body room: Room): Call<Void>

    @GET("/api/rooms/detail/{id}")
    fun getRoomDetail(@Path("id") roomId: Int): Call<Room>

    @GET("/api/rooms")
    fun getRooms(): Call<List<Room>>



    @Multipart
    @POST("/api/users/images")
    fun saveImage(@Part faceImageFile: MultipartBody.Part): Call<SaveImageResponse>

}