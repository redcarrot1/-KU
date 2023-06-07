package com.example.volunteerku.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.volunteerku.databinding.ActivityVolunteerSearchBinding

class VolunteerSearch : Fragment() {

    lateinit var binding: ActivityVolunteerSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityVolunteerSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

}