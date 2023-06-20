package com.example.volunteerku.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.volunteerku.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.addTextChangedListener { text ->
            binding.nextButton.isEnabled = text.toString().isNotEmpty() && binding.content.text.toString().isNotEmpty()
        }

        binding.content.addTextChangedListener { text ->
            binding.nextButton.isEnabled = binding.title.text.toString().isNotEmpty() && text.toString().isNotEmpty()
        }

        binding.nextButton.setOnClickListener {
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()
            val intent = Intent(this, Register2Activity::class.java)
            val url = getIntent().getStringExtra("itemUrl")

            intent.putExtra("title", title)
            intent.putExtra("content", content)
            intent.putExtra("itemUrl", url)
            println("register 1 : $url")

            startActivity(intent)

        }//다음 액티비티로 넘어가는 버튼

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}