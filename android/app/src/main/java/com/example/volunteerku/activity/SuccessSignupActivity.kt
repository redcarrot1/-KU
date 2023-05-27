package com.example.volunteerku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivitySuccessSignupBinding

class SuccessSignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySuccessSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gotoMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
