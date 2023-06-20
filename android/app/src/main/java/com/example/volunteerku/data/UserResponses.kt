package com.example.volunteerku.data

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName(value = "isSuccess") var isSuccess: Boolean,
    @SerializedName(value = "response") var response: Any
)

data class EmailCertifyCodeResponse(
    @SerializedName(value = "isAlreadyExist") var isAlreadyExist: Boolean?,
)

data class JWT(
    @SerializedName(value = "grantType") var grantType: String,
    @SerializedName(value = "accessToken") var accessToken: String,
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "nickname") var nickname: String,
    @SerializedName(value = "major") var major: String,
    @SerializedName(value = "currentVolunteerTime") var currentVolunteerTime: Int,
    @SerializedName(value = "introduce") var introduce: String,
)

data class DuplicateResponse(
    @SerializedName(value = "isDuplicate") var isDuplicate: Boolean,
)

data class EmailResponse(
    @SerializedName(value = "expireAt") var expireAt: String,
    @SerializedName(value = "sendingCount") var sendingCount: Int
)


data class EmailCertifyResponse(
    @SerializedName(value = "isSuccess") var isSuccess: Boolean,
    @SerializedName(value = "response") var response: Boolean
)

data class ExistEmailResponse(
    @SerializedName(value = "isExist") var isExist: Boolean,
)

data class ChangePasswordRequest(
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "password") var password: String,
)

data class SaveImageResponse(
    @SerializedName(value = "imageFileName") var imageFileName: String,
)

data class ChangeIntroductionRequest(
    @SerializedName(value = "introduction") var introduction: String,
)

data class ChangeNickName(
  @SerializedName(value = "nickname") var nickname: String,
)

data class MyVolunteerInfoRequest(
    @SerializedName(value = "minuteTime") var volunteerTime: Int,
    @SerializedName(value = "content") var volunteerTitle: String,
    @SerializedName(value = "date") var volunteerDate: String
)

data class SignupRequest(
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "password") var password: String,
    @SerializedName(value = "nickname") var nickname: String,
    @SerializedName(value = "major") var major: String,
    @SerializedName(value = "imageUrl") var imageUrl: String,
    @SerializedName(value = "introduction") var introduction: String,
)

