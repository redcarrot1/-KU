package com.example.volunteerku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityMypageBinding

class MypageActivity : AppCompatActivity() {

    lateinit var binding: ActivityMypageBinding

    var userName = "UserName";
    var userMajor = "재난지원대학 히어로학과"
    var introduce = "걱정하지마라 내가 왔다."


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        /*binding.userName.text = userName
        binding.departmentInfo.text = userMajor
        binding.multiAutoCompleteTextView.text = introduce*/
        binding.passwordChangeButton.setOnClickListener {
            gotoPasswordActivity()
        }
        binding.informationChangeButton.setOnClickListener {
            gotoMypageModifyActivity()
        }
    }

    private fun gotoMypageModifyActivity() {
        val Intent = Intent(this, MypageModifyActivity::class.java)
        startActivity(Intent)
    }

    private fun gotoPasswordActivity() {
        val Intent = Intent(this, PasswordActivity::class.java)
        startActivity(Intent)
    }

}