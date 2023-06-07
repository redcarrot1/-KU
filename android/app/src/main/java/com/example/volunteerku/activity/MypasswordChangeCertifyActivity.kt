package com.example.volunteerku.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityMypasswordChangeCertifyBinding

class MypasswordChangeCertifyActivity : AppCompatActivity() {
    lateinit var binding: ActivityMypasswordChangeCertifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypasswordChangeCertifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}