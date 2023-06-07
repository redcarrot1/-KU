package com.example.volunteerku.activity

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityMypageModifyBinding


class MypageModifyActivity : AppCompatActivity() {

    lateinit var binding: ActivityMypageModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.endEditButton.setOnClickListener {
            gotoMyPageActivity()
        }
    }

    private fun gotoMyPageActivity() {
        val Intent = Intent(this, MypageActivity::class.java)
        startActivity(Intent)
    }
}