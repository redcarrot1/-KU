package com.example.volunteerku.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.databinding.ActivityMypageBinding

class MypageActivity : Fragment() {

    lateinit var binding: ActivityMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMypageBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MyPageFragment())
            .commit()
    }
}