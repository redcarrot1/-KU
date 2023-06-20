package com.example.volunteerku.service


import com.example.volunteerku.data.Applications
import com.example.volunteerku.data.ChangePasswordRequest
import com.example.volunteerku.data.Detailresponse
import com.example.volunteerku.data.DuplicateResponse
import com.example.volunteerku.data.EmailCertifyCodeResponse
import com.example.volunteerku.data.ExistEmailResponse
import com.example.volunteerku.data.JWT
import com.example.volunteerku.data.EmailResponse
import com.example.volunteerku.data.MyVolunteerInfoRequest
import com.example.volunteerku.data.Room
import com.example.volunteerku.data.SaveImageResponse
import com.example.volunteerku.data.SignupRequest
import com.example.volunteerku.data.response
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    fun createPost(
        @Header("Authorization") accessToken: String,
        @Body room: Room
    ): Call<Void>

    @GET("/api/rooms/detail/{id}")
    fun getRoomDetail(@Path("id") roomId: Int): Call<Room>

    @POST("/api/rooms/applications")
    fun applyForVolunteerActivity(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): Call<Void>

    @GET("/api/rooms/applicationsview")
    fun getApplicationRooms(
        @Header("Authorization") accessToken: String
    ): Call<List<Applications>>


    @GET("/api/rooms")
    fun getRooms(): Call<List<Room>>



    @Multipart
    @POST("/api/users/images")
    fun saveImage(@Part faceImageFile: MultipartBody.Part): Call<SaveImageResponse>

    @POST("/api/users/change/introduction")
    fun changeIntroduction(
        @Header("Authorization") accessToken: String,
        @Body introduction: String
    ): Call<Void>

    @POST("/api/users/change/nickname")
    fun changeNickname(
        @Header("Authorization") accessToken: String,
        @Body nickname: String
    ): Call<Void>

    @POST("/api/volunteers")
    fun getMyVolunteerInfo(
        @Header("Authorization") accessToken: String,
    ): Call<List<MyVolunteerInfoRequest>>

    @GET("/api/volunteers/register")
    fun volunteerRegist(
        @Header("Authorization") accessToken: String,
        @Body request: MyVolunteerInfoRequest
    ): Call<Void>

}

interface VolunteerDataInterface{
    @GET("/openapi/service/rest/VolunteerPartcptnService/getVltrSearchWordList")
    fun volunteerSearch(): Call<response>


}

interface VolunteerDataDetailInterface{
    @GET("/openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem/")
    fun volunteerSearchDetail(
        @Query("progrmRegistNo") progrmRegistNo: String
    ): Call<Detailresponse>
}