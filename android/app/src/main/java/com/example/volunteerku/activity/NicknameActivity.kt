package com.example.volunteerku.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.pref
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.data.DuplicateResponse
import com.example.volunteerku.data.EmailCertifyCodeResponse
import com.example.volunteerku.databinding.ActivityNicknameBinding
import com.example.volunteerku.dialog.LoadingDialog
import com.example.volunteerku.service.UserService

class NicknameActivity : AppCompatActivity() {

    lateinit var binding: ActivityNicknameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nicknameEt.doAfterTextChanged {
            binding.nicknameEndBtn.isEnabled = !it.isNullOrBlank()
            nickname()
        }

        binding.nicknameEndBtn.setOnClickListener {
            savename()
        }
    }

    private fun nickname() {
        val nickname = binding.nicknameEt.text.toString()
        val text = binding.nicknameEt.text.toString()
        val regex = "^[ㄱ-ㅣ가-힣a-zA-Z]+\$".toRegex()

        val userService = UserService()
        userService.setOnResponseListener(object : UserService.OnResponseListener() {
            override fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
                if (isSuccess) {
                    if (body is DuplicateResponse) {
                        if (body.isDuplicate) { // 중복 닉네임
                            binding.nicknameGetCl.background = ContextCompat.getDrawable(
                                this@NicknameActivity,
                                R.drawable.edit_text_wrong
                            )
                            binding.nicknameEndBtn.isEnabled = false
                            binding.nicknameErOverlabTv.visibility = View.VISIBLE
                        } else if (!body.isDuplicate && text.matches(regex) && binding.nicknameEt.length() in 2..8) {
                            // 성공
                            binding.nicknameGetCl.background = ContextCompat.getDrawable(
                                this@NicknameActivity,
                                R.drawable.edit_text
                            )
                            binding.nicknameEndBtn.isEnabled = true
                            binding.nicknameErOverlabTv.visibility = View.INVISIBLE
                            binding.nicknameErRuleTv.visibility = View.INVISIBLE
                        } else {
                            // 중복은 아니나, 조건에 맞지 않음
                            binding.nicknameGetCl.background = ContextCompat.getDrawable(
                                this@NicknameActivity,
                                R.drawable.edit_text_wrong
                            )
                            binding.nicknameEndBtn.isEnabled = false
                            binding.nicknameErRuleTv.visibility = View.VISIBLE
                            binding.nicknameErOverlabTv.visibility = View.INVISIBLE
                        }
                        Log.d("retrofit", "getResponseBody: " + body.isDuplicate)
                    }
                } else {
                    Toast.makeText(applicationContext, err, Toast.LENGTH_SHORT).show()
                }
            }
        }).isDuplicate(nickname)
    }

    private fun savename() {
        val loading = LoadingDialog(this)
        loading.show()
        val userService = UserService()
        user.nickname = binding.nicknameEt.text.toString()
        userService.setOnResponseListener(object : UserService.OnResponseListener() {
            override fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
                loading.dismiss()
                if (isSuccess) {
                    if (body is EmailCertifyCodeResponse)
                    //user.emailCertifyCodeResponse = body.copy()
                        user.save(pref)

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()

                    Log.d("userInfo", "onCreate: $user")
                } else {
                    Log.d("retrofit", "getResponseBody: $err")
                    Toast.makeText(applicationContext, err, Toast.LENGTH_SHORT).show()
                }
            }
        }).register(user)
    }
}
