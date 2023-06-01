package com.example.volunteerku.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.VolunteerKUApplication
import com.example.volunteerku.data.SignupRequest
import com.example.volunteerku.databinding.ActivitySuccessSignupBinding
import com.example.volunteerku.service.UserService

class SuccessSignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySuccessSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        singup()

        binding.gotoMainBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun singup() {
        val secondIntent = intent
        val introduction = secondIntent.getStringExtra("introduction")
        val imageUrl = secondIntent.getStringExtra("imageUrl")

        val requestBody = SignupRequest(
            VolunteerKUApplication.user.email,
            VolunteerKUApplication.user.password,
            VolunteerKUApplication.user.nickname,
            VolunteerKUApplication.user.major,
            imageUrl!!,
            introduction!!
        )

        val userService = UserService()
        userService.setOnResponseListener(object : UserService.OnResponseListener() {
            override fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
                if (isSuccess) {
                    // nothing action
                } else {
                    Toast.makeText(applicationContext, err, Toast.LENGTH_SHORT).show()
                }
            }
        }).signup(requestBody)
    }
}
