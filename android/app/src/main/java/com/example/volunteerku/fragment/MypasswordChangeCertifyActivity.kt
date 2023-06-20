package com.example.volunteerku.fragment

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.example.volunteerku.databinding.ActivityMypasswordChangeCertifyBinding
import com.example.volunteerku.dialog.LoadingDialog
import com.example.volunteerku.service.UserService

class MypasswordChangeCertifyActivity : Fragment() {
    lateinit var binding: ActivityMypasswordChangeCertifyBinding
    var isSatisCond1: Boolean = false
    var isSatisCond2: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMypasswordChangeCertifyBinding.inflate(inflater, container, false)
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
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    binding.passwordEndBtn.isEnabled = false
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d("meg", "Text Change!!")
                    isSatisCond1 = s.toString().length in 6..15
                    isSatisCond2 = Regex("^(?=.*[A-Za-z])(?=.*\\d).+\$").matches(s.toString())

                    if (isSatisCond1) {
                        binding.condition1.setImageResource(R.drawable.ok_img)
                    } else {
                        binding.condition1.setImageResource(R.drawable.error_img)
                    }
                    if (isSatisCond2) {
                        binding.condition2.setImageResource(R.drawable.ok_img)
                    } else {
                        binding.condition2.setImageResource(R.drawable.error_img)
                    }
                }
            }
        )

        binding.passwordAgainEt.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    binding.passwordEndBtn.isEnabled = false
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                @SuppressLint("ResourceAsColor")
                override fun afterTextChanged(s: Editable?) {
                    if (isSatisCond1 && isSatisCond2) {
                        if (binding.passwordEt.getText().toString()
                                .equals(binding.passwordAgainEt.getText().toString())
                        ) {
                            binding.passwordNotSame.setText(R.string.password_same)
                            binding.passwordNotSame.setTextColor(R.color.green_main)
                            binding.passwordNotSame.visibility = View.VISIBLE

                            binding.passwordEndBtn.isEnabled = true

                        } else {
                            binding.passwordNotSame.setText(R.string.password_not_same)
                            binding.passwordNotSame.setTextColor(Color.RED)
                            binding.passwordNotSame.visibility = View.VISIBLE

                            binding.passwordEndBtn.isEnabled = false
                        }
                    }
                }
            }
        )

        binding.passwordEndBtn.setOnClickListener {
            changePassword(user.email, binding.passwordEt.text.toString())
        }
    }

    private fun changePassword(email: String, password: String) {
        // send email to server
        val loading = LoadingDialog(requireContext())
        loading.show()

        val userService = UserService()
        userService.setOnResponseListener(object : UserService.OnResponseListener() {
            @SuppressLint("SetTextI18n")
            override fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
                loading.dismiss()
                if (isSuccess) { // if code is correct
                    Toast.makeText(requireContext(), "비밀번호 변경 성공!", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, MyPageFragment())
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "비밀번호 변경 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }).changePassword(email, password)
    }

}