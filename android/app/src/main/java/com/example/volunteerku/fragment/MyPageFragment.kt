package com.example.volunteerku.fragment

import MyVolunteerListAvtivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.databinding.FragmentMyPageBinding

class MyPageFragment() : Fragment() {
    lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        textViewInit()
        buttonInit()
        println("JWT: ${user.jwt}")
        return binding.root
    }

    private fun textViewInit() {
        binding.userName.text = user.nickname
        binding.departmentInfo.text = user.major
        binding.multiAutoCompleteTextView.text = user.introduce

        binding.progressBar.min = 0
        binding.progressBar.max = 1860
        binding.progressBar.progress = user.currentVolunteerTime
    }

    private fun buttonInit() {
        binding.addVolunteerTime1.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyVolunteerAddActivity())
                .commit()
        }

        binding.passwordChangeButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MypasswordChangeActivity())
                .commit()
        }

        binding.informationChangeButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MypageModifyActivity())
                .commit()
        }
        binding.progressBar.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyVolunteerListAvtivity())
                .commit()
        }
    }
}
