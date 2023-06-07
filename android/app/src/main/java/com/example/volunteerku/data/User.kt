package com.example.volunteerku.data

import com.example.volunteerku.service.PreferenceUtil
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "nickname") var nickname: String,
    @SerializedName(value = "password") var password: String,
    @SerializedName(value = "major") var major: String,
    @SerializedName(value = "jwt") var jwt: String,
    @SerializedName(value = "introduce") var introduce: String,
    @SerializedName(value = "currentVolunteerTime") var currentVolunteerTime: Int
) {

    companion object {
        fun getDefaultUser(): User {
            return User(
                "null", "null", "null", "null",
                "null", "null", 0
            )
        }
    }

    fun save(pref: PreferenceUtil) {
        pref.setString("email", this.email)
        pref.setString("nickname", this.nickname)
        pref.setString("major", this.major)
        pref.setString("password", this.password)
        pref.setString("jwt", this.jwt)
    }

    fun load(pref: PreferenceUtil) {
        this.email = pref.getString("email", "null")
        this.nickname = pref.getString("nickname", "null")
        this.major = pref.getString("major", "null")
        this.major = pref.getString("password", "null")
        this.jwt = pref.getString("jwt", "null")
    }

    fun getAccessToken(): String {
        return "Bearer " + this.jwt
    }

}