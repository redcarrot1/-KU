package com.example.volunteerku

import android.app.Application
import android.util.Log
import com.example.volunteerku.data.User
import com.example.volunteerku.service.PreferenceUtil

class VolunteerKUApplication : Application() {

    companion object {
        lateinit var pref: PreferenceUtil
        var user = User.getDefaultUser()
    }

    override fun onCreate() {
        super.onCreate()
        pref = PreferenceUtil(applicationContext)
        user.load(pref)
        Log.d("userInfo", "onCreate: $user")
    }
}