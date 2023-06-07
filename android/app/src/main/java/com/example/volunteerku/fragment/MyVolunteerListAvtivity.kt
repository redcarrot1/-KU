package com.example.volunteerku.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.databinding.ActivityMyVolunteerAddBinding
import com.example.volunteerku.databinding.ActivityMyVolunteerListAvtivityBinding

class MyVolunteerListAvtivity : Fragment() {
    lateinit var binding: ActivityMyVolunteerListAvtivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMyVolunteerListAvtivityBinding.inflate(inflater, container, false)

        binding.addVolunteerTime1.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyVolunteerAddActivity())
                .commit()
        }

        return binding.root
    }

}