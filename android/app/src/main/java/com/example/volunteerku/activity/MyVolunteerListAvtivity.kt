package com.example.volunteerku.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityMyVolunteerListAvtivityBinding

class MyVolunteerListAvtivity : AppCompatActivity() {
    lateinit var binding: ActivityMyVolunteerListAvtivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyVolunteerListAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}