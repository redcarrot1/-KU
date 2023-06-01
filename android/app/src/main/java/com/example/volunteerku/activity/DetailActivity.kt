package com.example.volunteerku.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.volunteerku.databinding.ActivityDetailBinding
import com.example.volunteerku.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}