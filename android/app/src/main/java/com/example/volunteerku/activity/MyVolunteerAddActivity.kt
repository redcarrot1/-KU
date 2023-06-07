package com.example.volunteerku.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityMyVolunteerAddBinding

class MyVolunteerAddActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyVolunteerAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyVolunteerAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}