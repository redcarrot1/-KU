package com.example.volunteerku.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.volunteerku.databinding.ActivityDetailBinding
import com.example.volunteerku.databinding.ActivityListBinding
import com.example.volunteerku.databinding.ActivityMainBinding

class ListActivity : AppCompatActivity() {
    lateinit var binding : ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ToggleBtnDown.setOnClickListener{
            binding.ToggleBtnDown.visibility = View.GONE
            binding.ToggleBtnUp.visibility = View.VISIBLE
        }

        binding.ToggleBtnUp.setOnClickListener{
            binding.ToggleBtnUp.visibility = View.GONE
            binding.ToggleBtnDown.visibility = View.VISIBLE
        }
    }
}