package com.example.volunteerku.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityVolunteerItemDetailBinding

class VolunteerItemDetail : AppCompatActivity() {

    lateinit var binding: ActivityVolunteerItemDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}