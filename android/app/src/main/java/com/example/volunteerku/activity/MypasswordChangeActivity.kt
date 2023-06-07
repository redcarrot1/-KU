package com.example.volunteerku.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityMypasswordChangeBinding

class MypasswordChangeActivity : AppCompatActivity() {
    lateinit var binding: ActivityMypasswordChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}