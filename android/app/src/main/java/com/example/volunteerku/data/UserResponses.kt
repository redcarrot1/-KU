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
)

data class DuplicateResponse(
    @SerializedName(value = "isDuplicate") var isDuplicate: Boolean,
)

data class LogoutResponse(
    @SerializedName(value = "isSuccess") var isSuccess: Boolean?
)

data class RefreshTokenResponse(
    @SerializedName(value = "isSuccess") var isSuccess: Boolean?,
    @SerializedName(value = "response") var response: RefreshResponse
)

data class RefreshResponse(
    @SerializedName(value = "grantType") var grantType: String,
    @SerializedName(value = "accessToken") var accessToken: String,
    @SerializedName(value = "accessTokenExpireTime") var accessTokenExpireTime: String
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
