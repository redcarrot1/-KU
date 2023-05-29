package com.example.volunteerku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.nextButton.setOnClickListener {
            val title = binding.titleEt.text.toString()
            val content = binding.contentEt.text.toString()
            val intent = Intent(this, Register2Activity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("content", content)

            if(title.isNotEmpty() && content.isNotEmpty()) {
                startActivity(intent)
            }
        }//다음 액티비티로 넘어가는 버튼

            binding.backButton.setOnClickListener {
                finish()
            }
        }
    }
