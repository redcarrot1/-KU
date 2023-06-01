package com.example.volunteerku.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide.init
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.data.EmailResponse
import com.example.volunteerku.data.JWT
import com.example.volunteerku.databinding.ActivityLoginBinding
import com.example.volunteerku.databinding.ActivitySuccessChangePasswordBinding
import com.example.volunteerku.dialog.LoadingDialog
import com.example.volunteerku.service.UserService

class SuccessChangePasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivitySuccessChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init() {
        binding.gotoMainBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}