package com.example.volunteerku.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityVolunteerSearchBinding

class VolunteerSearch : AppCompatActivity() {

    lateinit var binding: ActivityVolunteerSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}