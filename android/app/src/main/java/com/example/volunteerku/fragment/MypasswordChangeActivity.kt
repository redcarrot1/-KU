package com.example.volunteerku.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.data.JWT
import com.example.volunteerku.databinding.ActivityMypasswordChangeBinding
import com.example.volunteerku.dialog.LoadingDialog
import com.example.volunteerku.service.UserService

class MypasswordChangeActivity : Fragment() {
    lateinit var binding: ActivityMypasswordChangeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMypasswordChangeBinding.inflate(inflater, container, false)
        init()
        binding.backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyPageFragment())
                .commit()
        }
        return binding.root
    }

    private fun init() {
        binding.userName.text = user.nickname
        binding.passwordEt.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    binding.passwordEndBtn.isEnabled = false
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    binding.passwordEndBtn.isEnabled = s.toString().length in 6..15
                }
            }
        )

        binding.passwordEndBtn.setOnClickListener {
            LoginResponse(user.email, binding.passwordEt.text.toString())
        }

    }

    private fun LoginResponse(email: String, password: String) {
        val loading = LoadingDialog(requireContext())
        loading.show()

        val userService = UserService()
        userService.setOnResponseListener(object : UserService.OnResponseListener() {
            @SuppressLint("SetTextI18n")
            override fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
                loading.dismiss()

                if (isSuccess) {
                    Log.d("login code", "Login`Response: code is correct")
                    if (body is JWT) { // login success
                        parentFragmentManager.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.fragmentContainer, MypasswordChangeCertifyActivity())
                            .commit()
                    } else {
                        Toast.makeText(requireContext(), "서버의 응답이 올바르지 않습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(requireContext(), "계정이 올바르지 않습니다!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        userService.signIn(email, password)
    }

}