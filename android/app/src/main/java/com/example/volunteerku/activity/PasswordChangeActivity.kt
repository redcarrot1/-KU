package com.example.volunteerku.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide.init
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.data.EmailCertifyCodeResponse
import com.example.volunteerku.data.EmailResponse
import com.example.volunteerku.databinding.ActivityPasswordChangeBinding
import com.example.volunteerku.dialog.LoadingDialog
import com.example.volunteerku.dialog.NotMemberDialog
import com.example.volunteerku.service.UserService

class PasswordChangeActivity : AppCompatActivity() {
    lateinit var binding: ActivityPasswordChangeBinding

    private var origin: String = ""
    private var again: String = ""
    var isSatisCond1: Boolean = false
    var isSatisCond2: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
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
            origin = binding.passwordEt.text.toString()
            again = binding.passwordAgainEt.text.toString()

            var email = user.email
            var password = origin
            changePassword(email, password)
        }
    }

    private fun changePassword(email: String, password: String) {
        // send email to server
        val loading = LoadingDialog(this)
        loading.show()

        val userService = UserService()
        userService.setOnResponseListener(object : UserService.OnResponseListener() {
            @SuppressLint("SetTextI18n")
            override fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
                loading.dismiss()
                if (isSuccess) { // if code is correct
                    Log.d("change password code", "changePassword: code is correct")
                    val intent = Intent(
                        this@PasswordChangeActivity,
                        SuccessChangePasswordActivity::class.java
                    )
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, err, Toast.LENGTH_SHORT).show()
                }
            }
        }).changePassword(email, password)
    }

}